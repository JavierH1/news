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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.List;

import cl.ucn.disc.dsm.jhidalgo.news.model.News;

/**
 * Testing the ContractImpl
 *
 * @author Javier Hidalgo Ochoa
 */
public class TestContractsImplFaker{


    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(TestContractsImplFaker.class);

    /**
     * The test of retrieve news.
     */
    @Test
    public void testRetrieveNews(){
        log.debug("Testing ..");

        // The concrete implementation
        Contracts contracts = new ContractsImplFaker();

        //Call the method ..
        List<News> news =  contracts.retrieveNews(5);

        // .. the list can't be null ..
        Assertions.assertNotNull(news,"List was Null :(");

        // .. the list can't be empty ..
        Assertions.assertTrue(news.size()!=0, "Empty list? :(");

        // .. the size(list) == 5 ..
        Assertions.assertTrue(news.size() ==5,"List size != 5 :(" );

        //debug to log
        for (News n : news){
            log.debug("News: {}",n);
        }

        // size = 0
        //Assertions.assertEquals(0,contracts.retrieveNews(0).size(),"List != 0");

        // size = 3
        //  Assertions.assertEquals(3,contracts.retrieveNews(3).size(),"List != 3");

        // size = 10
        // Assertions.assertTrue(contracts.retrieveNews(10).size() <= 10,"List != 10");

        log.debug("done");

    }

    /**
     * The test of save news.
     */

    @Test
    public void testSaveNews(){

        //The faker to use
        final Faker faker = Faker.instance();

        Contracts contracts = new ContractsImplFaker();

        News new_News = new News(
                Integer.toUnsignedLong(6),
                faker.book().title(),
                faker.name().username(),
                faker.name().fullName(),
                faker.internet().url(),
                faker.internet().avatar(),
                faker.harryPotter().quote(),
                faker.lorem().paragraph(3),
                ZonedDateTime.now(ZoneId.of("-3"))
        );

        News new_NewsDuplicate = new News(
                Integer.toUnsignedLong(1),
                faker.book().title(),
                faker.name().username(),
                faker.name().fullName(),
                faker.internet().url(),
                faker.internet().avatar(),
                faker.harryPotter().quote(),
                faker.lorem().paragraph(3),
                ZonedDateTime.now(ZoneId.of("-3"))
        );
        News newNews = null;

        //Storing a correct New
        contracts.saveNews(new_News);

        //Storing a valid New
        contracts.saveNews(new_NewsDuplicate);

        //Storing a null New
        contracts.saveNews(newNews);



    }


}
