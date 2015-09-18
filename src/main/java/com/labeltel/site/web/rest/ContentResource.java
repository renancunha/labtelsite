package com.labeltel.site.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labeltel.site.domain.Content;
import com.labeltel.site.repository.ContentRepository;
import com.labeltel.site.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Content.
 */
@RestController
@RequestMapping("/api")
public class ContentResource {

    private final Logger log = LoggerFactory.getLogger(ContentResource.class);

    @Inject
    private ContentRepository contentRepository;

    /**
     * POST  /contents -> Create a new content.
     */
    @RequestMapping(value = "/contents",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Content> create(@RequestBody Content content) throws URISyntaxException {
        log.debug("REST request to save Content : {}", content);
        if (content.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new content cannot already have an ID").body(null);
        }
        Content existentContent = contentRepository.findByName(content.getName());

        if(existentContent != null) {
            content.setId(existentContent.getId());
        }

        Content result = contentRepository.save(content);
        return ResponseEntity.created(new URI("/api/contents/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("content", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /contents -> Updates an existing content.
     */
    @RequestMapping(value = "/contents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Content> update(@RequestBody Content content) throws URISyntaxException {
        log.debug("REST request to update Content : {}", content);
        if (content.getId() == null) {
            return create(content);
        }
        Content result = contentRepository.save(content);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("content", content.getId().toString()))
                .body(result);
    }

    /**
     * GET  /contents -> get all the contents.
     */
    @RequestMapping(value = "/contents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Content> getAll() {
        log.debug("REST request to get all Contents");
        return contentRepository.findAll();
    }

    /**
     * GET  /content_by_name -> get content by name
     */
    @RequestMapping(value = "/content_by_name/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Content getByName(@PathVariable String name) {
        log.debug("REST request to get content by name");
        return contentRepository.findByName(name);
    }

    /**
     * GET  /contents/:id -> get the "id" content.
     */
    @RequestMapping(value = "/contents/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Content> get(@PathVariable Long id) {
        log.debug("REST request to get Content : {}", id);
        return Optional.ofNullable(contentRepository.findOne(id))
            .map(content -> new ResponseEntity<>(
                content,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contents/:id -> delete the "id" content.
     */
    @RequestMapping(value = "/contents/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Content : {}", id);
        contentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("content", id.toString())).build();
    }
}
