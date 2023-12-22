package Repositories.File;

import Models.Filees;

import java.sql.SQLException;

public interface FileRepository {

    void saveFile(Filees file) throws SQLException;
    Filees fileByDetails(long fileId);
    String getFileNameByFileId(long fileId);

    String getFileTypeByFileId(long fileId);

    byte[] getFileContentByFileId(long fileId);
}
