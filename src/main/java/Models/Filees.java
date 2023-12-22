package Models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Filees {
    private long FileId;
    private long UserId;
    private String FileName;
    private String FileType;
    private byte[] FileContent;
    private Date FileUploadDate;
}
