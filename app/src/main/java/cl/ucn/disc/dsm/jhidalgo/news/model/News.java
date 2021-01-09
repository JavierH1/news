/*
 * Copyright 2020 Javier Hidalgo Ochoa, javier.hidalgo@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.jhidalgo.news.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import net.openhft.hashing.LongHashFunction;

import org.threeten.bp.ZonedDateTime;

import cl.ucn.disc.dsm.jhidalgo.news.utils.Validation;

/**
 * The domain model: News.
 * @author Javier Hidalgo Ochoa.
 */
@Entity
public final class News {

    /**
     * Unique id
     */
    @PrimaryKey (autoGenerate = true)
    private Long id;

    /**
     * The Tittle.
     * Restrictions: not null, size > 2.
     */
    @ColumnInfo(name = "title")
    private final String title;

    /**
     * The source
     * Restrictions: not null, size > 2.
     */
    @ColumnInfo(name = "source")
    private final String source;

    /**
     * The author.
     * Restrictions: not null, size > 2.
     */
    @ColumnInfo(name = "author")
    private final String author;

    /**
     * The URL.
     */
    @ColumnInfo(name = "url")
    private final String url;

    /**
     * The URL of the image.
     */
    @ColumnInfo(name = "urlImage")
    private final String urlImage;

    /**
     * The description.
     */
    @ColumnInfo(name = "description")
    private final String description;

    /**
     * The content.
     * Restrictions: not null.
     */
    @ColumnInfo(name = "content")
    private final String content;

    /**
     * The date of publish.
     * Restrictions: not null.
     */
    @ColumnInfo(name = "publishedAt")
    private final ZonedDateTime publishedAt;

    /**
     * The constructor.
     * @param title
     * @param source
     * @param author
     * @param url
     * @param urlImage
     * @param description
     * @param content
     * @param publishedAt
     */
    public News(String title, String source, String author, String url, String urlImage, String description, String content, ZonedDateTime publishedAt) {

        // Validation of title
        Validation.minSize(title,2,"title");
        this.title = title;

        // Validation of source
        Validation.minSize(source,2,"source");
        this.source = source;

        // Validation of author
        Validation.minSize(author,3,"author");
        this.author = author;

        // Apply the xxHash function
        this.id = LongHashFunction.xx().hashChars(title + '|' + source + '|' + author);

        // Can't be null
        this.url = url;
        this.urlImage = urlImage;

        Validation.minSize(description,10, "description");
        this.description = description;

        Validation.notNull(content, "content");
        this.content = content;

        Validation.notNull(publishedAt, "publishedAt");
        this.publishedAt = publishedAt;
    }

    /**
     * @return the Id.
     */
    public Long getId() { return id; }

    /**
     * @return the tittle.
     */
    public String getTitle() { return title; }

    /**
     * @return the source.
     */
    public String getSource() { return source; }

    /**
     * @return the author.
     */
    public String getAuthor() { return author; }

    /**
     * @return the URL.
     */
    public String getUrl() { return url; }

    /**
     * @return the URL of the image.
     */
    public String getUrlImage() { return urlImage; }

    /**
     * @return the description.
     */
    public String getDescription() { return description; }

    /**
     * @return the content.
     */
    public String getContent() { return content; }

    /**
     * @return the date of publish.
     */
    public ZonedDateTime getPublishedAt() { return publishedAt; }

    public void setId(Long id) {
        this.id = id;
    }
}
