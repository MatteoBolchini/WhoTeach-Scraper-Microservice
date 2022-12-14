package it.whoteach.connector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.secretmanager.v1.Secret;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretName;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.opencsv.bean.CsvToBeanBuilder;

import it.whoteach.scraper.dto.ArticleDto;
import it.whoteach.scraper.exception.BadRequestException;
import it.whoteach.scraper.pojo.Article;
import it.whoteach.scraper.repository.ArticleRepository;

@Service
public class GoogleCloudConnector {

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	ModelMapper modelMapper;

	private String serviceAccount;
	private List<String> scope;
	private Storage storage;

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

	public void getSecret() throws IOException {
		try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
			SecretName secretName = SecretName.of("neo4j-project-dev", "testing");
			Secret secret = client.getSecret(secretName); // permission denied
			System.out.printf("Secret %s", secret.getName());
		}
	}

	public void retrieveCsv(String fileName) {
		Blob blob = getStorage().get(BlobId.of("test-bucket-csv-to-neo4j-1", fileName));
		ReadChannel readChannel = blob.reader();
		List<ArticleDto> articles = new CsvToBeanBuilder<ArticleDto>(Channels.newReader(readChannel, "UTF-8"))
				.withSeparator(';')
				.withIgnoreQuotations(false)
				.withType(ArticleDto.class)
				.build()
				.parse();
		for(ArticleDto a : articles) {
			articleRepository.save(this.modelMapper.map(a, Article.class));
		}
	}
}
