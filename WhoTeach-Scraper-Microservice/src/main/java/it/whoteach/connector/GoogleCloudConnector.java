package it.whoteach.connector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class GoogleCloudConnector {

	@Autowired
	private Storage storage;

	public Blob retrieveCsv(String fileName) {
		storage = StorageOptions.newBuilder().setProjectId("neo4j-project-dev").build().getService();

	    Blob blob = storage.get(BlobId.of("test-bucket-csv-to-neo4j-1", fileName));
	    return blob;
	}
}
