ALTER TABLE client
    DROP COLUMN authorizationGrantTypes;

ALTER TABLE client
    DROP COLUMN clientAuthenticationMethods;

ALTER TABLE client
    DROP COLUMN clientId;

ALTER TABLE client
    DROP COLUMN clientIdIssuedAt;

ALTER TABLE client
    DROP COLUMN clientName;

ALTER TABLE client
    DROP COLUMN clientSecret;

ALTER TABLE client
    DROP COLUMN clientSecretExpiresAt;

ALTER TABLE client
    DROP COLUMN clientSettings;

ALTER TABLE client
    DROP COLUMN postLogoutRedirectUris;

ALTER TABLE client
    DROP COLUMN redirectUris;

ALTER TABLE client
    DROP COLUMN tokenSettings;