package fr.archives.nat;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.params.Parameters;

public class ESIndex {

	private JestClient client;
	private JsonParser jsonParser;

	private ESIndex(Builder builder) {
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(builder.getUri()).multiThreaded(true).build());
		client = factory.getObject();
		jsonParser = new JsonParser();
	}

	public boolean createDocument(JsonNode document, String index, String type, String id) {
		try {
			JsonObject objectFromString = jsonParser.parse(document.toString()).getAsJsonObject();
			Index query = new Index.Builder(objectFromString).setParameter(Parameters.OP_TYPE, "create").index(index)
					.type(type).id(id).build();
			JestResult result = client.execute(query);
			return result.isSucceeded();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean deleteDocument(String index, String type, String id) {
		try {
			Delete query = new Delete.Builder(id).index(index).type(type).build();
			JestResult result = client.execute(query);
			return result.isSucceeded();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public JsonNode getDocument(String index, String type, String id) {
		try {
			Get get = new Get.Builder(index, id).type(type).build();
			JestResult result = client.execute(get);
			if (result.isSucceeded()) {
				return JsonHandler.tranform(result.getJsonObject());
			} else {
				throw new RuntimeException(result.getErrorMessage());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public JsonNode getDocuments(JsonNode searchQuery) {
		try {
			JsonNode query = searchQuery.get("query");
			String index = searchQuery.get("index").asText();
			String type = searchQuery.get("type").asText();
			Search search = new Search.Builder(query.toString()).addIndex(index).addType(type).build();
			SearchResult result = client.execute(search);
			return JsonHandler.tranform(result.getJsonObject());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean createIndex(String index) {
		try {
			CreateIndex query = new CreateIndex.Builder(index).build();
			JestResult result = client.execute(query);
			return result.isSucceeded();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean deleteIndex(String index) {
		try {
			DeleteIndex query = new DeleteIndex.Builder(index).build();
			JestResult result = client.execute(query);
			return result.isSucceeded();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public JestClient getClient() {
		return client;
	}

	public void close() throws Exception {
		client.close();
	}

	public static class Builder {

		private String uri;

		public Builder(String uri) {
			this.uri = uri;
		}

		public String getUri() {
			return uri;
		}

		public ESIndex build() {
			ESIndex result = new ESIndex(this);
			return result;
		}
	}

}