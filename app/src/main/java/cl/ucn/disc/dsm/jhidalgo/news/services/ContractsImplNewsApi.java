/*
 * Copyright 2020 Javier Hidalgo Ochoa, javier.hidalgo@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.jhidalgo.news.services;

import com.kwabenaberko.newsapilib.models.Article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cl.ucn.disc.dsm.jhidalgo.news.model.News;
import cl.ucn.disc.dsm.jhidalgo.news.utils.Validation;

/**
 * The NewsApi implementation of Contracts.
 * @author Javier Hidalgo Ochoa
 */
public class ContractsImplNewsApi implements Contracts {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(ContractsImplNewsApi.class);

    private final NewsApiService newsApiService;

    /**
     * The Constructor.
     *
     * @param apiKey
     */
    public ContractsImplNewsApi(String apiKey) {

        Validation.minSize(apiKey, 10, "ApiKey !!");
        this.newsApiService = new NewsApiService(apiKey);

    }

    /**
     * Get the list of News.
     *
     * @param size size of the list.
     * @return the list of News.
     */
    @Override
    public List<News> retrieveNews(Integer size) {

        try {

            // Get the list of Article
            List<Article> articles = newsApiService.getTopHeadlines(
                    "technology", size
            );

            // The List of Articles to List of News
            List<News> rawNews = new ArrayList<>();
            for (Article article : articles) {
                // log.debug("Article: {}", ToStringBuilder.reflectionToString(article, ToStringStyle.MULTI_LINE_STYLE));
                rawNews.add(article2news(article));
            }

            // Return the news filtered and sorted by date
            return rawNews.stream()
                    // Remove the duplicateds (by keys)
                    .filter(distintById(News::getId))
                    // Order by date
                    .sorted((k1, k2) -> k2.getPublishedAt().compareTo(k1.getPublishedAt()))
                    .collect(Collectors.toList());


        } catch (IOException ex) {
            // Encapsulate!
            throw new RuntimeException(ex);
        }

    }

    /**
     * Filter the Stream.
     *
     * @param idExtractor
     * @param <T>   news to filter
     * @return true if the news already exist.
     */
    private static <T> Predicate<T> distintById(Function<? super T, ?> idExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(idExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * Article to News. (Transformer Pattern).
     *
     * @param article to convert.
     * @return the News.
     */
    private static News article2news(Article article) {

        Validation.notNull(article, "Article null !?!");

        // Fixing the author null :(
        if(article.getAuthor() == null || article.getAuthor().length() ==  0){
            article.setAuthor("No Author*");
        }

        // Fixing more restrictions
        if(article.getDescription() == null || article.getDescription().length() == 0 ){
            article.setDescription("No Description*");
        }


        // The date
        ZonedDateTime publishedAt = ZonedDateTime
                .parse(article.getPublishedAt())
                .withZoneSameInstant(ZoneId.of("-3"));


        return new News(
                article.getTitle(),
                article.getSource().getName(),
                article.getAuthor(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getDescription(),
                article.getDescription(),// FIXME: WHERE IS THE CONTENT?
                publishedAt

        );
    }

    /**
     * Save a List of News into the System.
     *
     * @param db the database.
     * @param listNews to save.
     */
    @Override
    public void saveNews(AppDatabase db, List<News> listNews) {

        for (int i=0;i<listNews.size();i++) {
            db.newsDao().insert(listNews.get(i));
        }

    }
}
