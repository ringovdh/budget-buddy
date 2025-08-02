package be.yorian.budgetbuddy.controller.impl;

import be.yorian.budgetbuddy.dto.comment.CommentDTO;
import be.yorian.budgetbuddy.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static be.yorian.budgetbuddy.mother.CommentMother.commentDtoCarrefour;
import static be.yorian.budgetbuddy.mother.CommentMother.commentDtoEasySave;
import static be.yorian.budgetbuddy.mother.CommentMother.newCommentDTO;
import static be.yorian.budgetbuddy.mother.CommentMother.savedNewCommentDTO;
import static be.yorian.budgetbuddy.mother.CommentMother.savedUpdatedCommentDTO;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentControllerImpl.class)
class CommentControllerImplTest extends BaseControllerTest {

    private static final String COMMENTS_URL = "/comments/";
    private static final String GET_COMMENTS_ALL_URL = COMMENTS_URL + "all";
    private static final String GET_COMMENT_URL = COMMENTS_URL + "{commentId}";
    private static final String GET_COMMENTS_SEARCHTERM_URL = COMMENTS_URL + "searchterm";
    private static final String COMMENT_NOT_FOUND = "Comment not found";


    @MockitoBean
    private CommentService commentService;


    @Test
    @DisplayName("Get comments should return all comments")
    void getComments_returnsAllComments() throws Exception {
        List<CommentDTO> comments = List.of(commentDtoCarrefour(), commentDtoEasySave());
        when(commentService.getComments()).thenReturn(comments);

        mockMvc.perform(get(GET_COMMENTS_ALL_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(comments.get(0).id().intValue())))
                .andExpect(jsonPath("$[0].searchterm", is(comments.get(0).searchterm())))
                .andExpect(jsonPath("$[1].id", is(comments.get(1).id().intValue())))
                .andExpect(jsonPath("$[1].searchterm", is(comments.get(1).searchterm())));

        verify(commentService, times(1)).getComments();
    }

    @Test
    @DisplayName("Get comment by id should return correct comment when exists")
    void getCommentById_whenExists_shouldReturnComment() throws Exception {
        CommentDTO comment = commentDtoCarrefour();
        when(commentService.getCommentById(comment.id())).thenReturn(comment);

        mockMvc.perform(get(GET_COMMENT_URL, comment.id())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(comment.id().intValue())))
                .andExpect(jsonPath("$.searchterm", is(comment.searchterm())))
                .andExpect(jsonPath("$.categoryId", is(comment.categoryId().intValue())));

        verify(commentService, times(1)).getCommentById(comment.id());
    }

    @Test
    @DisplayName("Get comment by id should return exception when not exists")
    void getCommentById_whenNotExists_shouldReturnException() throws Exception {
        long unknownCategoryId = 99L;

        when(commentService.getCommentById(unknownCategoryId))
                .thenThrow(new EntityNotFoundException(COMMENT_NOT_FOUND));

        ResultActions resultActions = mockMvc.perform(get(GET_COMMENT_URL, unknownCategoryId)
                        .accept(MediaType.APPLICATION_JSON));

        expectNotFound(resultActions, COMMENT_NOT_FOUND);
        verify(commentService, times(1)).getCommentById(unknownCategoryId);
    }

    @Test
    @DisplayName("Get comment by searchterm should return comments when searchterm contains comment")
    void getCommentBySearchterm_shouldReturnComments_whenSearchtermContainsComment() throws Exception {
        String searchTerm = "carref";
        Page<CommentDTO> page = new PageImpl<>(List.of(commentDtoCarrefour()));

        when(commentService.getCommentsBySearchterm(searchTerm, 0, 10))
                .thenReturn(page);

        mockMvc.perform(get(GET_COMMENTS_SEARCHTERM_URL)
                        .param("searchterm", searchTerm)
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].searchterm", is("Carrefour")));
    }

    @Test
    @DisplayName("Create comment should return saved comment and location")
    void createNewComment_shouldCreateComment_returnComment() throws Exception {
        CommentDTO newComment = newCommentDTO();
        CommentDTO savedComment = savedNewCommentDTO();

        when(commentService.createComment(any(CommentDTO.class))).thenReturn(savedComment);

        mockMvc.perform(post(COMMENTS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newComment)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith(COMMENTS_URL + savedComment.id())))
                .andExpect(jsonPath("$.id", is(savedComment.id().intValue())))
                .andExpect(jsonPath("$.searchterm", is(savedComment.searchterm())))
                .andExpect(jsonPath("$.replacement", is(savedComment.replacement())))
                .andExpect(jsonPath("$.categoryId", is(savedComment.categoryId().intValue())));
    }

    @Test
    @DisplayName("Update comment should return updated comment")
    void updateComment_shouldReturnUpdatedComment() throws Exception {
        CommentDTO updatedComment = savedUpdatedCommentDTO();
        when(commentService.updateComment(any(Long.class), any(CommentDTO.class)))
                .thenReturn(updatedComment);

        mockMvc.perform(put(GET_COMMENT_URL, updatedComment.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedComment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedComment.id().intValue())))
                .andExpect(jsonPath("$.replacement", is("Aangepaste commentaar")));

        verify(commentService, times(1)).updateComment(eq(updatedComment.id()), any(CommentDTO.class));
    }

    @Test
    @DisplayName("Update comment should return exception when not exists")
    void updateComment_shouldReturnException_whenCommentNotExists() throws Exception {
        CommentDTO updatedComment = savedUpdatedCommentDTO();
        long unknownCommentId = 99L;
        when(commentService.updateComment(any(Long.class), any(CommentDTO.class)))
                .thenThrow(new EntityNotFoundException(COMMENT_NOT_FOUND));

        ResultActions resultActions = mockMvc.perform(put(GET_COMMENT_URL, unknownCommentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedComment)));

        expectNotFound(resultActions, COMMENT_NOT_FOUND);
        verify(commentService, times(1)).updateComment(eq(unknownCommentId), any(CommentDTO.class));
    }

    @Test
    @DisplayName("Delete comment should return no content when comment is deleted")
    void deleteComment_shouldReturnNoContent_whenCommentDeleted() throws Exception {
        long commentId = 1L;
        doNothing().when(commentService).deleteComment(commentId);

        mockMvc.perform(delete(GET_COMMENT_URL, commentId))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).deleteComment(commentId);
    }

    @Test
    @DisplayName("Delete comment should return exception when not exists")
    void deleteComment_shouldReturnException_whenCommentNotExists() throws Exception {
        long unknownCommentId = 99L;
        doThrow(new EntityNotFoundException(COMMENT_NOT_FOUND))
                .when(commentService).deleteComment(unknownCommentId);

        ResultActions resultActions = mockMvc.perform(delete(GET_COMMENT_URL, unknownCommentId));

        expectNotFound(resultActions, COMMENT_NOT_FOUND);
        verify(commentService, times(1)).deleteComment(unknownCommentId);
    }


}