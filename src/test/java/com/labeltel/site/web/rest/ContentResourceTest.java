package com.labeltel.site.web.rest;

import com.labeltel.site.Application;
import com.labeltel.site.domain.Content;
import com.labeltel.site.repository.ContentRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ContentResource REST controller.
 *
 * @see ContentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ContentResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_HTML = "SAMPLE_TEXT";
    private static final String UPDATED_HTML = "UPDATED_TEXT";
    private static final String DEFAULT_LABEL = "SAMPLE_TEXT";
    private static final String UPDATED_LABEL = "UPDATED_TEXT";

    @Inject
    private ContentRepository contentRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restContentMockMvc;

    private Content content;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContentResource contentResource = new ContentResource();
        ReflectionTestUtils.setField(contentResource, "contentRepository", contentRepository);
        this.restContentMockMvc = MockMvcBuilders.standaloneSetup(contentResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        content = new Content();
        content.setName(DEFAULT_NAME);
        content.setHtml(DEFAULT_HTML);
        content.setLabel(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createContent() throws Exception {
        int databaseSizeBeforeCreate = contentRepository.findAll().size();

        // Create the Content

        restContentMockMvc.perform(post("/api/contents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(content)))
                .andExpect(status().isCreated());

        // Validate the Content in the database
        List<Content> contents = contentRepository.findAll();
        assertThat(contents).hasSize(databaseSizeBeforeCreate + 1);
        Content testContent = contents.get(contents.size() - 1);
        assertThat(testContent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContent.getHtml()).isEqualTo(DEFAULT_HTML);
        assertThat(testContent.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void getAllContents() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get all the contents
        restContentMockMvc.perform(get("/api/contents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(content.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].html").value(hasItem(DEFAULT_HTML.toString())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", content.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(content.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.html").value(DEFAULT_HTML.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContent() throws Exception {
        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

		int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Update the content
        content.setName(UPDATED_NAME);
        content.setHtml(UPDATED_HTML);
        content.setLabel(UPDATED_LABEL);
        

        restContentMockMvc.perform(put("/api/contents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(content)))
                .andExpect(status().isOk());

        // Validate the Content in the database
        List<Content> contents = contentRepository.findAll();
        assertThat(contents).hasSize(databaseSizeBeforeUpdate);
        Content testContent = contents.get(contents.size() - 1);
        assertThat(testContent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContent.getHtml()).isEqualTo(UPDATED_HTML);
        assertThat(testContent.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void deleteContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

		int databaseSizeBeforeDelete = contentRepository.findAll().size();

        // Get the content
        restContentMockMvc.perform(delete("/api/contents/{id}", content.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Content> contents = contentRepository.findAll();
        assertThat(contents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
