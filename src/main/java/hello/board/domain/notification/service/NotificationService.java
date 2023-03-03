package hello.board.domain.notification.service;

import hello.board.controller.notification.dto.req.NotificationUpdateReqDto;
import hello.board.controller.notification.dto.req.NotificationWriteDto;
import hello.board.controller.notification.dto.res.NotificationResDto;
import hello.board.controller.notification.dto.res.NotificationUpdateResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.MemberRepository;
import hello.board.domain.notification.entity.Notification;
import hello.board.domain.notification.repository.NotificationRepository;
import hello.board.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    public NotificationResDto findById(Long id) {
        return new NotificationResDto(findNotification(id));
    }

    public List<NotificationResDto> findAll() {
        return notificationRepository.findAll()
                .stream()
                .map(NotificationResDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public NotificationResDto makeNotice(NotificationWriteDto writeDto, Long memberId) {
        return new NotificationResDto(saveNotification(writeDto, memberId));
    }

    private Notification saveNotification(NotificationWriteDto writeDto, Long memberId) {
        return notificationRepository.save(createNotification(writeDto, memberId));
    }

    private Notification createNotification(NotificationWriteDto writeDto, Long memberId) {
        return Notification.builder()
                .content(writeDto.getContent())
                .writer(findMember(memberId).getName())
                .build();
    }

    @Transactional
    public NotificationUpdateResDto updateNotification(Long noticeId, NotificationUpdateReqDto UpdateReqDto) {
        Notification updateNotification = findNotification(noticeId);
        updateNotification.updateInfo(UpdateReqDto);
        return new NotificationUpdateResDto(updateNotification);
    }

    @Transactional
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",memberId));
                });
    }

    private Notification findNotification(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",id));
                });
    }
}
