package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.dto.comment.CommentDTO;
import be.yorian.budgetbuddy.entity.Comment;
import be.yorian.budgetbuddy.repository.CategoryRepository;
import be.yorian.budgetbuddy.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static be.yorian.budgetbuddy.mother.CategoryMother.categoryGrocery;
import static be.yorian.budgetbuddy.mother.CommentMother.commentCarrefour;
import static be.yorian.budgetbuddy.mother.CommentMother.commentDtoCarrefour;
import static be.yorian.budgetbuddy.mother.CommentMother.easySaveComment;
import static be.yorian.budgetbuddy.mother.CommentMother.newComment;
import static be.yorian.budgetbuddy.mother.CommentMother.newCommentDTO;
import static be.yorian.budgetbuddy.mother.CommentMother.updatedCommentDtoCarrefour;
import static be.yorian.budgetbuddy.mother.CommentMother.updatedCommentCarrefourUnknownCategoryDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    private Comment carrefour;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CommentServiceImpl commentService;


    @BeforeEach
    void setUp() {
        carrefour = commentCarrefour();
    }

    @Test
    @DisplayName("Get comments should return all comments")
    void getComments_returnsAllComments() {
        Comment saving = easySaveComment();
        List<Comment> comments = List.of(carrefour, saving);

        when(commentRepository.findAll()).thenReturn(comments);

        List<CommentDTO> commentDtos = commentService.getComments();

        assertThat(commentDtos)
                .hasSize(2)
                .extracting(CommentDTO::searchterm)
                .containsExactlyInAnyOrder("Carrefour", "EasySave");
        verify(commentRepository).findAll();
    }

    @Test
    @DisplayName("Get comment by id should return correct comment when exists")
    void getCommentById_whenExists_shouldReturnComment() {
        Long commentId = carrefour.getId();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(carrefour));

        CommentDTO commentDto = commentService.getCommentById(commentId);

        assertCommentDto(commentDto, commentDtoCarrefour());
        verify(commentRepository).findById(commentId);
    }

    @Test
    @DisplayName("Get comment by id should throw exception when not exists")
    void getCommentById_whenNotExists_shouldThrowException() {
        Long unknownCommentId = 99L;

        when(commentRepository.findById(unknownCommentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> commentService.getCommentById(unknownCommentId));
        assertThat(exception.getMessage()).isEqualTo("comment_not_found");
    }

    @Test
    @DisplayName("Get comments by searchterm should return comments when searchterm contains comment")
    void getCommentsBySearchterm_shouldReturnComments_whenSearchtermContainsComment() {
        Page<Comment> page = new PageImpl<>(List.of(carrefour));
        String searchterm = "carref";

        when(commentRepository.findBySearchtermContainingIgnoreCase(
                eq(searchterm),
                any(Pageable.class))).thenReturn(page);

        Page<CommentDTO> comments = commentService.getCommentsBySearchterm(searchterm, 0, 10);

        assertThat(comments.getContent())
                .hasSize(1)
                .extracting(CommentDTO::searchterm)
                .contains(carrefour.getSearchterm());
        verify(commentRepository).findBySearchtermContainingIgnoreCase(eq(searchterm), any(Pageable.class));
    }

    private void assertCommentDto(CommentDTO actualcomment, CommentDTO expectedComment) {
        assertThat(actualcomment).usingRecursiveComparison()
                .isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("Post comment should return saved comment and location")
    void createNewComment_shouldCreateComment_andReturnComment() {
        CommentDTO newCommentDto = newCommentDTO();
        Comment newComment = newComment();

        when(commentRepository.save(any(Comment.class))).thenReturn(newComment);
        when(categoryRepository.findById(newCommentDto.categoryId())).thenReturn(Optional.of(categoryGrocery()));

        CommentDTO savedComment = commentService.createComment(newCommentDto);

        assertCommentDto(savedComment, newCommentDto);
        verify(commentRepository).save(any(Comment.class));
        verify(categoryRepository).findById(newCommentDto.categoryId());
    }

    @Test
    @DisplayName("Put comment should return updated comment")
    void updateComment_shouldReturnUpdatedComment() {
        CommentDTO updatedCommentDto = updatedCommentDtoCarrefour();

        when(commentRepository.findById(carrefour.getId())).thenReturn(Optional.of(carrefour));
        when(categoryRepository.findById(updatedCommentDto.categoryId())).thenReturn(Optional.of(categoryGrocery()));

        CommentDTO commentDTO = commentService.updateComment(carrefour.getId(), updatedCommentDto);

        assertCommentDto(commentDTO, updatedCommentDto);

        verify(commentRepository).findById(carrefour.getId());
        verify(categoryRepository).findById(updatedCommentDto.categoryId());
    }

    @Test
    @DisplayName("Put comment should throw exception when not exists")
    void updateComment_shouldThrowException_whenCommentNotExists() {
        CommentDTO updatedCommentDto = updatedCommentDtoCarrefour();
        long unknownCommentId = 99L;

        when(commentRepository.findById(unknownCommentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> commentService.updateComment(unknownCommentId, updatedCommentDto));

        assertThat(exception.getMessage()).isEqualTo("comment_not_found");

        verify(commentRepository).findById(unknownCommentId);
        verify(categoryRepository, never()).findById(updatedCommentDto.categoryId());
        verify(commentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Put comment should throw exception when category not exists")
    void updateComment_shouldThrowException_whenCategroryNotExists() {
        CommentDTO updatedCommentDto = updatedCommentCarrefourUnknownCategoryDto();

        when(commentRepository.findById(carrefour.getId())).thenReturn(Optional.of(carrefour));
        when(categoryRepository.findById(updatedCommentDto.categoryId())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> commentService.updateComment(updatedCommentDto.id(), updatedCommentDto));

        assertThat(exception.getMessage()).isEqualTo("Category not found with id: 99");

        verify(commentRepository).findById(updatedCommentDto.id());
        verify(categoryRepository).findById(updatedCommentDto.categoryId());
        verify(commentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Delete comment should delete comment when exists")
    void deleteComment_shouldDeleteComment_whenExists() {
        when(commentRepository.findById(carrefour.getId())).thenReturn(Optional.of(carrefour));

        commentService.deleteComment(carrefour.getId());

        verify(commentRepository).findById(carrefour.getId());
        verify(commentRepository, times(1)).delete(carrefour);
    }

    @Test
    @DisplayName("Delete comment should throw exception when not exists")
    void deleteComment_shouldReturnException_whenNotExists() {
        long unknownCommentId = 99L;

        when(commentRepository.findById(unknownCommentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> commentService.deleteComment(unknownCommentId));

        assertThat(exception.getMessage()).isEqualTo("comment_not_found");
        verify(commentRepository).findById(unknownCommentId);
        verify(commentRepository, never()).delete(any());
    }

}
