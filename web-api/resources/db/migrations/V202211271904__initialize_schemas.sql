CREATE TABLE "public"."extensions" (
  "extension" varchar(20) NOT NULL,
  "account_code" varchar(255) COLLATE "pg_catalog"."default",
  "call_timeout" int4 NOT NULL,
  "domain" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "enabled" bool NOT NULL,
  "force_ping" bool NOT NULL,
  "limit_destination" varchar(255) COLLATE "pg_catalog"."default",
  "limit_max" int4 NOT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."extensions" ADD CONSTRAINT "extensions_pkey" PRIMARY KEY ("extension");
