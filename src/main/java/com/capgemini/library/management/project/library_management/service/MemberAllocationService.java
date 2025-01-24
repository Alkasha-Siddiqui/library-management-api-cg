package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.model.MemberDTO;
import java.util.List;

public interface MemberAllocationService {

    MemberDTO registerMember(MemberDTO memberDTO);
    List<MemberDTO> getAllMembers(String status);
}
