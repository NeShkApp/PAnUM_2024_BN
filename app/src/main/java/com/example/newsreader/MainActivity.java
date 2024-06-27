package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<NewsItem> news;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private Spinner spinnerSort;
    private Spinner categorySpinner;
    private ImageView imgChangeSize, btnScrollToTop;

    private String[] categories = {"Formula 1", "Rally", "MotoGP"};
    private String[] urls = {
            "https://www.autosport.com/rss/f1/news/",
            "https://www.autosport.com/rss/category/rally/news/",
            "https://www.autosport.com/rss/motogp/news/"
    };

    private boolean isGrid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        news = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new NewsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        ImageView btnScrollToTop = findViewById(R.id.btnScrollToTop);
        btnScrollToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                    if (firstVisiblePosition > 0) {
                        btnScrollToTop.setVisibility(View.VISIBLE);
                    } else {
                        btnScrollToTop.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        spinnerSort = findViewById(R.id.spinnerSort);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(spinnerAdapter);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortNews(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter2);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new GetNews(urls[position]).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ImageView imgChangeSize = findViewById(R.id.imgChangeSize);
        imgChangeSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGrid = !isGrid;
                if (isGrid) {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    adapter.setLayout(R.layout.news_item_small);
                } else {
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter.setLayout(R.layout.news_item);
                }
                adapter.notifyDataSetChanged();
            }
        });

        new GetNews(urls[0]).execute();
    }

    private void sortNews(int position) {
        if (news.isEmpty()) return;

        if (position == 1) {
            Collections.sort(news, new Comparator<NewsItem>() {
                @Override
                public int compare(NewsItem o1, NewsItem o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
            if (isGrid) {
                Collections.reverse(news);
            }
        } else if (position == 0) {
            Collections.sort(news, new Comparator<NewsItem>() {
                @Override
                public int compare(NewsItem o1, NewsItem o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
            if (isGrid) {
                Collections.reverse(news);
            }
        }
        adapter.setNews(news);
    }


    private class GetNews extends AsyncTask<Void, Void, Void> {
        private String urlString;

        public GetNews(String urlString) {
            this.urlString = urlString;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream inputStream = getInputStream();
            if (inputStream != null) {
                try {
                    initXMLPullParser(inputStream);
                } catch (XmlPullParserException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Collections.sort(news, new Comparator<NewsItem>() {
                @Override
                public int compare(NewsItem o1, NewsItem o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
            adapter.setNews(news);
        }


        private void initXMLPullParser(InputStream inputStream) throws XmlPullParserException, IOException {
            Log.d(TAG, "initXMLPullParser: Started");
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.next();

            news.clear();

            parser.require(XmlPullParser.START_TAG, null, "rss");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                parser.require(XmlPullParser.START_TAG, null, "channel");
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    if (parser.getName().equals("item")) {
                        parser.require(XmlPullParser.START_TAG, null, "item");

                        String title = "";
                        String description = "";
                        String link = "";
                        String date = "";
                        String imageUrl = "";

                        while (parser.next() != XmlPullParser.END_TAG) {
                            if (parser.getEventType() != XmlPullParser.START_TAG) {
                                continue;
                            }

                            String tagName = parser.getName();
                            if (tagName.equals("title")) {
                                title = getContent(parser, "title");
                            } else if (tagName.equals("description")) {
                                description = getContent(parser, "description");
                            } else if (tagName.equals("link")) {
                                link = getContent(parser, "link");
                            } else if (tagName.equals("pubDate")) {
                                date = getContent(parser, "pubDate");
                                date = date.substring(0, date.length() - 6);
                            } else if (tagName.equals("enclosure")) {
                                imageUrl = parser.getAttributeValue(null, "url");
                                parser.nextTag();
                            } else {
                                skipTag(parser);
                            }
                        }

                        NewsItem item = new NewsItem(title, description, link, date, imageUrl);
                        news.add(item);
                    } else {
                        skipTag(parser);
                    }
                }
            }
        }

        private String getContent(XmlPullParser parser, String tagName) throws XmlPullParserException, IOException {
            parser.require(XmlPullParser.START_TAG, null, tagName);
            String content = "";
            if (parser.next() == XmlPullParser.TEXT) {
                content = parser.getText();
                parser.nextTag();
            } else if (parser.getEventType() == XmlPullParser.CDSECT) {
                content = parser.getText();
                parser.nextTag();
            }


            content = content.replaceAll("<!\\[CDATA\\[", "").replaceAll("\\]\\]>", "");
            content = content.replaceAll("<a[^>]*>.*?</a>", "");

            return content;
        }


        private void skipTag(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }

            int number = 1;

            while (number != 0) {
                switch (parser.next()) {
                    case XmlPullParser.START_TAG:
                        number++;
                        break;
                    case XmlPullParser.END_TAG:
                        number--;
                        break;
                    default:
                        break;
                }
            }
        }

        private InputStream getInputStream() {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                return connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
