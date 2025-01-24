package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.entity.Genre;
import com.capgemini.library.management.project.library_management.entity.Member;
import com.capgemini.library.management.project.library_management.model.GenreResponseDTO;
import com.capgemini.library.management.project.library_management.model.MemberDTO;
import com.capgemini.library.management.project.library_management.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "memberService")
@Transactional
public class MemberAllocationServiceImpl implements MemberAllocationService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MemberDTO registerMember(MemberDTO memberDTO) {
        Member member = modelMapper.map(memberDTO, Member.class);
        Member savedMember = memberRepository.save(member);
        MemberDTO memberResponseDTO = modelMapper.map(savedMember, MemberDTO.class);
        return memberResponseDTO;
    }

    @Override
    public List<MemberDTO> getAllMembers(String status) {
        List<Member> members;

        if (status == null) {
            members = memberRepository.findAll();
        } else {
            members = memberRepository.findByStatus(Member.MemberStatus.valueOf(status));
        }

        return members.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());
    }
}