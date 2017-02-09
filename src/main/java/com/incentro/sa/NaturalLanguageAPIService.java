package com.incentro.sa;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.language.v1beta1.CloudNaturalLanguageAPI;
import com.google.api.services.language.v1beta1.model.*;
import com.incentro.sa.models.GoogleUser;

import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

/**
 * A sample application that uses the Natural Language API to perform
 * entity, sentiment and syntax analysis.
 */
@SuppressWarnings("serial")
public class NaturalLanguageAPIService {
    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "gmail-sa";

    private static final int MAX_RESULTS = 4;

    /**
     * Detects entities,sentiment and syntax in a document using the Natural Language API.
     */

    /**
     * Print a list of {@code entities}.
     */
    public static void printEntities(PrintStream out, List<Entity> entities) {
        if (entities == null || entities.size() == 0) {
            out.println("No entities found.");
            return;
        }
        out.printf("Found %d entit%s.\n", entities.size(), entities.size() == 1 ? "y" : "ies");
        for (Entity entity : entities) {
            out.printf("%s\n", entity.getName());
            out.printf("\tSalience: %.3f\n", entity.getSalience());
            out.printf("\tType: %s\n", entity.getType());
            if (entity.getMetadata() != null) {
                for (Map.Entry<String, String> metadata : entity.getMetadata().entrySet()) {
                    out.printf("\tMetadata: %s = %s\n", metadata.getKey(), metadata.getValue());
                }
            }
        }
    }

    /**
     * Print the Sentiment {@code sentiment}.
     */
    public static void printSentiment(PrintStream out, Sentiment sentiment) {
        if (sentiment == null) {
            out.println("No sentiment found");
            return;
        }
        out.println("Found sentiment.");
        out.printf("\tMagnitude: %.3f\n", sentiment.getMagnitude());
        out.printf("\tPolarity: %.3f\n", sentiment.getPolarity());
    }

    public static void printSyntax(PrintStream out, List<Token> tokens) {
        if (tokens == null || tokens.size() == 0) {
            out.println("No syntax found");
            return;
        }
        out.printf("Found %d token%s.\n", tokens.size(), tokens.size() == 1 ? "" : "s");
        for (Token token : tokens) {
            out.println("TextSpan");
            out.printf("\tText: %s\n", token.getText().getContent());
            out.printf("\tBeginOffset: %d\n", token.getText().getBeginOffset());
            out.printf("Lemma: %s\n", token.getLemma());
            out.printf("PartOfSpeechTag: %s\n", token.getPartOfSpeech().getTag());
            out.println("DependencyEdge");
            out.printf("\tHeadTokenIndex: %d\n", token.getDependencyEdge().getHeadTokenIndex());
            out.printf("\tLabel: %s\n", token.getDependencyEdge().getLabel());
        }
    }

    /**
     * Connects to the Natural Language API using Application Default Credentials.
     */
    public static CloudNaturalLanguageAPI getLanguageService(GoogleUser gu)
            throws IOException, GeneralSecurityException {

        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        final Credential credential =  GmailAPIService.getCredentialObject(gu);
        return new CloudNaturalLanguageAPI.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                jsonFactory, new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
                credential.initialize(request);
            }
        })
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private final CloudNaturalLanguageAPI languageApi;

    /**
     * Constructs a {@link NaturalLanguageAPIService} which connects to the Cloud Natural Language API.
     */
    public NaturalLanguageAPIService(CloudNaturalLanguageAPI languageApi) {
        this.languageApi = languageApi;
    }

    /**
     * Gets {@link Entity}s from the string {@code text}.
     */
    public List<Entity> analyzeEntities(String text) throws IOException {
        AnalyzeEntitiesRequest request =
                new AnalyzeEntitiesRequest()
                        .setDocument(new Document().setContent(text).setType("PLAIN_TEXT"))
                        .setEncodingType("UTF16");
        CloudNaturalLanguageAPI.Documents.AnalyzeEntities analyze =
                languageApi.documents().analyzeEntities(request);

        AnalyzeEntitiesResponse response = analyze.execute();
        return response.getEntities();
    }

    /**
     * Gets {@link Sentiment} from the string {@code text}.
     */
    public Sentiment analyzeSentiment(String text) throws IOException {
        AnalyzeSentimentRequest request =
                new AnalyzeSentimentRequest()
                        .setDocument(new Document().setContent(text).setType("PLAIN_TEXT"));
        CloudNaturalLanguageAPI.Documents.AnalyzeSentiment analyze =
                languageApi.documents().analyzeSentiment(request);

        AnalyzeSentimentResponse response = analyze.execute();
        return response.getDocumentSentiment();
    }

    /**
     * Gets {@link Token}s from the string {@code text}.
     */
    public List<Token> analyzeSyntax(String text) throws IOException {
        AnnotateTextRequest request =
                new AnnotateTextRequest()
                        .setDocument(new Document().setContent(text).setType("PLAIN_TEXT"))
                        .setFeatures(new Features().setExtractSyntax(true))
                        .setEncodingType("UTF16");
        CloudNaturalLanguageAPI.Documents.AnnotateText analyze =
                languageApi.documents().annotateText(request);

        AnnotateTextResponse response = analyze.execute();
        return response.getTokens();
    }
}