package com.solomarket.service;

import com.solomarket.dto.FileDto;
import com.solomarket.entity.FileEntity;
import com.solomarket.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : com.solomarket.service
 * fileName       : FileService
 * author         : 이동하
 * date           : 25. 4. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 3.        이동하       최초 생성
 */
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public List<FileDto> getFilesByProductNo(Long productNo) {
        return fileRepository.findByProductNo(productNo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void saveFiles(List<FileDto> files) {
        List<FileEntity> entities = files.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        fileRepository.saveAll(entities);
    }

    private FileDto toDTO(FileEntity entity) {
        FileDto dto = new FileDto();
        dto.setFileNo(entity.getFileNo());
        dto.setProductNo(entity.getProductNo());
        dto.setFileName(entity.getFileName());
        dto.setFileUrl(entity.getFileUrl());
        dto.setFileType(entity.getFileType());
        dto.setFileSize(entity.getFileSize());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }

    private FileEntity toEntity(FileDto dto) {
        return FileEntity.builder()
                .fileNo(dto.getFileNo())
                .productNo(dto.getProductNo())
                .fileName(dto.getFileName())
                .fileUrl(dto.getFileUrl())
                .fileType(dto.getFileType())
                .fileSize(dto.getFileSize())
                .sortOrder(dto.getSortOrder())
                .build();
    }
}
