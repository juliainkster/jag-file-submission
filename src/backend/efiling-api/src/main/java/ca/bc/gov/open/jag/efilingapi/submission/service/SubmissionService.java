package ca.bc.gov.open.jag.efilingapi.submission.service;

import ca.bc.gov.open.jag.efilingapi.api.model.GenerateUrlRequest;
import ca.bc.gov.open.jag.efilingapi.submission.models.Submission;

import java.util.UUID;

public interface SubmissionService {

    Submission generateFromRequest(UUID authUserId, GenerateUrlRequest generateUrlRequest);

}
