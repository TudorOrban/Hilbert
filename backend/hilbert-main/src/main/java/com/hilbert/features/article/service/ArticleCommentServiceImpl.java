package com.hilbert.features.article.service;

import com.hilbert.core.user.model.User;
import com.hilbert.core.user.repository.UserRepository;
import com.hilbert.features.article.dto.ArticleCommentDto;
import com.hilbert.features.article.dto.ArticleCommentMapper;
import com.hilbert.features.article.dto.CreateArticleCommentDto;
import com.hilbert.features.article.model.ArticleComment;
import com.hilbert.features.article.repository.ArticleCommentRepository;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.sanitization.service.EntitySanitizerService;
import com.hilbert.shared.search.models.ArticleCommentSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final UserRepository userRepository;
    private final EntitySanitizerService sanitizationService;

    @Autowired
    public ArticleCommentServiceImpl(
        ArticleCommentRepository articleCommentRepository,
        UserRepository userRepository,
        EntitySanitizerService entitySanitizerService
    ) {
        this.articleCommentRepository = articleCommentRepository;
        this.userRepository = userRepository;
        this.sanitizationService = entitySanitizerService;
    }

    public PaginatedResults<ArticleCommentDto> searchComments(ArticleCommentSearchParams searchParams) {
        PaginatedResults<ArticleComment> results = articleCommentRepository.searchComments(searchParams);

        return new PaginatedResults<>(
                results.getResults().stream().map(this::mapCommentToCommentDto).toList(),
                results.getTotalCount()
        );
    }

    public ArticleCommentDto createComment(CreateArticleCommentDto commentDto) {
        CreateArticleCommentDto sanitizedDto = sanitizationService.sanitizeCreateArticleCommentDto(commentDto);
        User user = userRepository.findById(sanitizedDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedDto.getUserId().toString(), ResourceType.USER, ResourceIdentifierType.ID));

        ArticleComment comment = this.mapCreateCommentDtoToComment(sanitizedDto);
        comment.setUsername(user.getUsername());

        ArticleComment savedComment = articleCommentRepository.save(comment);

        return this.mapCommentToCommentDto(savedComment);
    }

    public void deleteComment(Long id) {
        ArticleComment existingComment = articleCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.COMMENT, ResourceIdentifierType.ID));

        articleCommentRepository.delete(existingComment);
    }

    private ArticleCommentDto mapCommentToCommentDto(ArticleComment comment) {
        return ArticleCommentMapper.INSTANCE.commentToCommentSearchDto(comment);
    }

    private ArticleComment mapCreateCommentDtoToComment(CreateArticleCommentDto commentDto) {
        return ArticleCommentMapper.INSTANCE.createCommentDtoToComment(commentDto);
    }
}
