package it.whoteach.scraper.connector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.opencsv.bean.CsvToBeanBuilder;

import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.exception.BadRequestException;

@Service
public class GoogleCloudConnector {
	
	private GoogleCloudConnector() {}
	
	@Value("${whoteach.cloudstorage.sa}")
	private String serviceAccount;
	@Value("${whoteach.cloudstorage.scope}")
	private List<String> scope;
	@Value("${whoteach.cloudstorage.bucket-name}")
	private String bucketname;
	private Storage storage;

	/**
	 * @throws a BadRequestException if it is not able to create the Google Credentials
	 * @return the Storage specified
	 */
	public Storage getStorage() throws BadRequestException {
		if (this.storage != null) {
			return this.storage;
		}
		GoogleCredentials googleCredential = null;
		try {
			InputStream googleClousStorageServiceAccountStream = 
					new ByteArrayInputStream(this.serviceAccount.getBytes(StandardCharsets.UTF_8));
			googleCredential = GoogleCredentials.fromStream(googleClousStorageServiceAccountStream).createScoped(this.scope);
			this.storage = StorageOptions.newBuilder().setCredentials(googleCredential).build().getService();
		} catch (IOException e) {
			throw new BadRequestException("Unable to create the Google Credentials for cloud storage");
		}
		return this.storage;
	}
	
	/**
	 * @param fileName name of the csv to read
	 * @return a list of ArticleDtos
	 */
	public List<ArticleDto> retrieveCsv(String fileName) {
		Blob blob = getStorage().get(BlobId.of(bucketname, fileName));
		ReadChannel readChannel = blob.reader();
		List<ArticleDto> articles = new CsvToBeanBuilder<ArticleDto>(Channels.newReader(readChannel, "UTF-8"))
				.withSeparator(';')
				.withIgnoreQuotations(false)
				.withType(ArticleDto.class)
				.build()
				.parse();
		return articles;
	}
}
