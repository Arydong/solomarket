package com.solomarket.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * packageName    : com.solomarket.entity
 * fileName       : FileEntity
 * author         : 이동하
 * date           : 25. 4. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 3.        이동하       최초 생성
 */
@Entity
@Table(name = "tbl_file")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileNo;

    @Column(nullable = false)
    private int productNo;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    private String fileType;

    private Long fileSize;

    private Integer sortOrder;

    private LocalDateTime createdAt;
}