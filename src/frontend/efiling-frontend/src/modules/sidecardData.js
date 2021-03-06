/* eslint-disable react/jsx-one-expression-per-line */
import React from "react";
import { MdInfoOutline, MdPerson, MdTimer } from "react-icons/md";

const aboutCso = {
  heading: "About E-File Submission",
  content: [
    <p key="aboutCso">
      E-File submission is a service to help you securely and electronically
      file documents with the Government of British Columbia Court Services
      Online (CSO).&nbsp;
      <a
        href="https://justice.gov.bc.ca/cso/about/index.do"
        target="_blank"
        rel="noopener noreferrer"
      >
        Learn more about CSO
      </a>
      .
    </p>
  ],
  type: "bluegrey",
  isWide: true,
  icon: <MdInfoOutline className="side-card-icon" />
};

const csoAccountDetails = {
  heading: "Your CSO Account",
  content: [
    <p key="csoAccountDetails">
      CSO account <b>blah</b> is linked to your Basic BCeID account&nbsp;
      <b>blah2</b>
      &nbsp;and will be used to file documents.&nbsp;
      {/* TODO: fix url and blahs */}
      <a
        href="https://justice.gov.bc.ca/cso/about/index.do"
        target="_blank"
        rel="noopener noreferrer"
      >
        View your CSO account details
      </a>
      .
    </p>
  ],
  type: "bluegrey",
  isWide: true,
  icon: <MdPerson className="side-card-icon" />
};

const rushSubmission = {
  heading: "Rush/Urgent Submission",
  content: [
    <p key="rushSubmission">
      If you wish to request that this package be submitted on an urgent (rush)
      basis, you must provide a reason for your request to be considered.&nbsp;
      {/* TODO: fix url and blahs */}
      <a
        href="https://justice.gov.bc.ca/cso/about/index.do"
        target="_blank"
        rel="noopener noreferrer"
      >
        Request rush submission
      </a>
      .
    </p>
  ],
  type: "bluegrey",
  isWide: true,
  icon: <MdTimer className="side-card-icon" />
};

export function getSidecardData() {
  return {
    aboutCso,
    csoAccountDetails,
    rushSubmission
  };
}
