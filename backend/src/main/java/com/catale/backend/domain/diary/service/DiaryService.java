package com.catale.backend.domain.diary.service;

import com.catale.backend.domain.diary.dto.DiaryGetRequestDto;
import com.catale.backend.domain.diary.dto.DiaryGetResponseDto;
import com.catale.backend.domain.diary.entity.Diary;
import com.catale.backend.domain.diary.repository.DiaryRepository;
import com.catale.backend.domain.member.repository.MemberRepository;
import com.catale.backend.global.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryService {
    @Autowired
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public DiaryGetResponseDto getDiary(Long id){
        Diary diary = diaryRepository.findById(id).orElseThrow(NullPointerException::new);
        return new DiaryGetResponseDto(diary);
    }
    @Transactional
    public void postDiary(Long memberId, DiaryGetRequestDto dto){
        Diary diary = Diary.builder()
                .member(memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new))
//                .cocktail()
                .mood(dto.getMood())
                .comment(dto.getComment())
                .reason(dto.getReason())
                .month(dto.getMonth())
                .emotion1(dto.getEmotion1())
                .emotion2(dto.getEmotion2())
                .emotion3(dto.getEmotion3())
                .build();
        diaryRepository.save(diary);
    }
    @Transactional
    public void deleteDiary(Long diaryId){
        diaryRepository.deleteById(diaryId);
    }


}
