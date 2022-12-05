package luxoft.codingchallange.enums;

import lombok.Getter;

@Getter
public enum FileAttributes {
    NAME,
    SIZE,
    TIME_CREATED,
    PERMISSIONS;

    FileAttributes() {

    }
}
