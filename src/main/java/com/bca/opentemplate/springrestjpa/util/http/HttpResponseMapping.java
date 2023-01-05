package com.bca.opentemplate.springrestjpa.util.http;

import org.springframework.http.HttpStatus;

import com.bca.opentemplate.springrestjpa.model.dto.eai.EaiErrorMessageDto;
import com.bca.opentemplate.springrestjpa.model.dto.eai.EaiErrorSchemeDto;

public enum HttpResponseMapping {
    // General error
    OK(HttpStatus.OK, "ERR-200", "Success", "Sukses"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "ERR-400", "Invalid argument", "Argumen tidak valid"),
    UNSSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "ERR-415", "Media type not supported",
            "Tipe media tidak didukung"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "ERR-401", "Invalid credential", "Kredensial yang diberikan salah"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "ERR-403", "Access denied", "Akses ditolak"),
    INVALID_JSON(HttpStatus.BAD_REQUEST, "ERR-490", "Invalid JSON format", "Struktur atau format JSON tidak valid"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ERR-500", "Server error", "Terjadi kesalahan"),
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "ERR-408", "Request timeout", "Waktu permintaan habis"),
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "ERR-504", "Gateway timeout", "Waktu koneksi gateway habis"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "ERR-405", "HTTP method not allowed",
            "Metode HTTP tidak diijinkan"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "ERR-404", "Data not found", "Data tidak ditemukan"),

    // Document Error
    DOCUMENT_EXIST_CHAINED(HttpStatus.OK, "ERR-209", "Document already exist and now related to current input", "Dokumen sudah ada dan sudah dilakukan relasi ke input"),
    DOCUMENT_CONFLICT(HttpStatus.CONFLICT, "ERR-409",
            "Duplicate document name, please rename the uploaded document",
            "Terdapat dokumen dengan nama yang sama, mohon dapat mengubah nama dokumen yang diupload"),

    // Document Error
    DOCUMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR-404", "Document not found", "Dokumen tidak ditemukan");

    private final HttpStatus httpStatus;
    private final EaiErrorSchemeDto eaiErrorSchemeDto;

    HttpResponseMapping(HttpStatus httpStatus, String errorCode, String messageEnglish, String messageIndonesian) {
        this.httpStatus = httpStatus;
        this.eaiErrorSchemeDto = new EaiErrorSchemeDto(errorCode,
                new EaiErrorMessageDto(messageIndonesian, messageEnglish));
    }

    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    public EaiErrorSchemeDto eaiErrorSchemeDto() {
        return this.eaiErrorSchemeDto;
    }

}
