package com.solomarket.dao;

import com.solomarket.dto.InquiryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryDao {

    void insertInquiry(InquiryDto inquiryDto);

    List<InquiryDto> findByUserNo(int userNo);

    InquiryDto findByInquiryId(int inquiryId);

}
