package com.solomarket.service;

import com.solomarket.dao.InquiryDao;
import com.solomarket.dto.InquiryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryDao inquiryDao;

    public void createInquiry(InquiryDto inquiryDto) {
        inquiryDao.insertInquiry(inquiryDto);
    }

    public List<InquiryDto> getInquiryListByUserNo(int userNo) {
        return inquiryDao.findByUserNo(userNo);
    }

    public InquiryDto getInquiryById(int inquiryId) {
        return inquiryDao.findByInquiryId(inquiryId);
    }

}
