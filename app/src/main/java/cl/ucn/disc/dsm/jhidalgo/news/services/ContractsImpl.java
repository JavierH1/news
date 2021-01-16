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

import com.github.javafaker.Faker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.jhidalgo.news.model.News;

/**
 * @author Javier Hidalgo Ochoa
 */
public class ContractsImpl implements Contracts {


    /**
     * The logger
     */
    private static final Logger log = LoggerFactory.getLogger(ContractsImpl.class);

    /**
     * Get the list of News.
     *
     * @param size size of the list.
     * @return the list of News.
     */
    @Override
    public List<News> retrieveNews(Integer size) {

        // T he list of news
        final List<News> news = new ArrayList<>();

        // TODO: add the faker news to the list

        for(int i=0;i < 5;i++){
            Faker faker = Faker.instance();

            Long number =Long.MIN_VALUE;

            ZonedDateTime time = ZonedDateTime.parse("2012-06-30T12:30:40Z[UTC]");

            News news1 = new News(faker.lorem().sentence(), faker.app().name(), faker.name().fullName(),faker.internet().url(),faker.internet().url(),faker.lorem().sentence(),faker.lorem().paragraph(),time);
            news.add(news1);

        }
        return news;
    }

    /**
     * Save a List of News into the System.
     *
     * @param db the database.
     * @param listNews to save.
     */
    public void saveNews(AppDatabase db, List<News> listNews) {


    }
}
