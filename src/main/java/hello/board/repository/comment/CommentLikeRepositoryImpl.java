package hello.board.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.entity.comment.CommentLike;
import hello.board.entity.comment.QCommentLike;

import javax.persistence.EntityManager;

public class CommentLikeRepositoryImpl implements CommentLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommentLikeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public boolean hasNoLike(Long commentId, Long memberId) {
        CommentLike commentLike = queryFactory
                .select(QCommentLike.commentLike)
                .from(QCommentLike.commentLike)
                .where(QCommentLike.commentLike.comment.id.eq(commentId), QCommentLike.commentLike.member.id.eq(memberId))
                .fetchOne();
        return commentLike == null;
    }

}
