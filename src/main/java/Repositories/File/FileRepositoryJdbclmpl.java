package Repositories.File;

import Models.Filees;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileRepositoryJdbclmpl implements FileRepository{

    private final Connection connection;
    private static final String SQL_INSERT = "insert into Files(user_id,file_name, file_type, file_content, upload_date) values";

    public FileRepositoryJdbclmpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveFile(Filees file) throws SQLException {

        String sql = SQL_INSERT + "(?,?,?,?, CURRENT_DATE)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,file.getUserId());
        preparedStatement.setString(2, file.getFileName());
        preparedStatement.setString(3, file.getFileType());
        preparedStatement.setBytes(4, file.getFileContent());

        preparedStatement.executeUpdate();
        System.out.println("File Executed");

    }

    @Override
    public Filees fileByDetails(long fileId) {

        Filees files = null;
        String sql = "SELECT file_name, file_type FROM files WHERE file_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, fileId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String fileName = resultSet.getString("file_name");
                String fileType = resultSet.getString("file_type");

                files = Filees.builder()
                        .FileName(fileName)
                        .FileType(fileType)
                        .build();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return files;
    }

    @Override
    public String getFileNameByFileId(long fileId) {
        String sql = "SELECT file_name FROM files WHERE file_id = ?";
        String fileName = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, fileId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                fileName = resultSet.getString("file_name");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }

    @Override
    public String getFileTypeByFileId(long fileId) {
        String sql = "SELECT file_type FROM files WHERE file_id = ?";
        String fileType = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, fileId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                fileType = resultSet.getString("file_type");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return fileType;
    }

    @Override
    public byte[] getFileContentByFileId(long fileId) {
        String sql = "SELECT file_content FROM files WHERE file_id = ?";
        byte[] fileContent = new byte[0];

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, fileId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                fileContent = resultSet.getString("file_content").getBytes();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return fileContent;
    }
}
