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

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        Validation.notNull(apiKey,"ApiKey");
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
            // Request to NewApi
            List<Article> articles = this.newsApiService.getTopHeadlines("general", size);

            // The final list of news
            List<News> news = new ArrayList<>();

            // Iterate over the articles
            for(Article article: articles){

                // Article -> news
                news.add(article2news(article));

            }
            // Return the list of news
            return news;



        } catch (IOException e) {
            log.error("Error", e);
            // Inner Exception
            throw new RuntimeException(e);
        }

    }

    /**
     * Article to News. (Transformer Pattern).
     * @param article to convert.
     * @return the News.
     */
    private static News article2news(Article article) {

        // The date
        ZonedDateTime publishedAt = ZonedDateTime
                .parse(article.getPublishedAt())
                .withZoneSameInstant(ZoneId.of("-3"));

        // Fixing the restrictions
        if(article.getAuthor() == null || article.getAuthor().length() <=4){
            article.setAuthor("No Author*");
        }

        // Fixing more restrictions
        if(article.getDescription() == null || article.getDescription().length() <= 9 ){
            article.setDescription("No Description*");
        }

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
     * Save one News into the System.
     *
     * @param news to save.
     */
    @Override
    public void saveNews(News news) {
        throw new NotImplementedException("Can't save news in NewsAPI");

    }
}
