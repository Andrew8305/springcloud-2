package de.codeboje.springbootbook.commentstore.restapi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.codeboje.springbootbook.commentstore.service.CommentService;
import de.codeboje.springbootbook.commons.CommentDTO;
import de.codeboje.springbootbook.model.CommentModel;
import io.swagger.annotations.ApiOperation;

@RestController
public class ReadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReadController.class);

	@Autowired
	private CommentService service;

	@Autowired
	private CounterService counterService;

	@Bean
	public AlwaysSampler alwaysSampler() {
		return new AlwaysSampler();
	}

	@RequestMapping(value = "/list/{id}")
	@ApiOperation(value = "Find student by id", notes = "Also returns a link to retrieve all students with rel - all-students")
	public List<CommentDTO> getComments(@PathVariable(value = "id") String pageId) throws IOException {
		LOGGER.info("get comments for pageId {}", pageId);
		counterService.increment("commentstore.list_comments");
		List<CommentModel> r = service.list(pageId);
		if (r.isEmpty()) {
			LOGGER.info("get comments for pageId {} - not found", pageId);
			throw new FileNotFoundException("/list/" + pageId);
		}

		LOGGER.info("get comments for pageId {} - done", pageId);
		return transformToDTO(r);
	}

	@RequestMapping(value = "/listspam/{id}")
	@ApiOperation(value = "Find student by id", notes = "Also returns a link to retrieve all students with rel - all-students")
	public List<CommentDTO> getSpamComments(@PathVariable(value = "id") String pageId) throws IOException {
		LOGGER.info("get spam comments for pageId {}", pageId);
		counterService.increment("commentstore.list_comments");
		List<CommentModel> r = service.listSpamComments(pageId);
		LOGGER.info("get spam comments for pageId {} - done", pageId);
		return transformToDTO(r);
	}

	private List<CommentDTO> transformToDTO(List<CommentModel> r) {
		List<CommentDTO> result = new LinkedList<CommentDTO>();

		for (CommentModel m : r) {
			CommentDTO dto = new CommentDTO();
			dto.setId(m.getId());
			dto.setUsername(m.getUsername());
			dto.setEmailAddress(m.getEmailAddress());
			dto.setComment(m.getComment());
			dto.setCreated(m.getCreationDate());
			result.add(dto);
		}
		return result;
	}

	@ExceptionHandler(FileNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public void handle404(Exception ex, Locale locale) {
		LOGGER.debug("Resource not found {}", ex.getMessage());
	}
}
