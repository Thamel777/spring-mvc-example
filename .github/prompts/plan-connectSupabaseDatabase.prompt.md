# Plan: Connect Supabase (PostgreSQL) to Spring Boot Project

Supabase is a hosted PostgreSQL database. The connection uses the standard PostgreSQL JDBC driver. You'll use Spring profiles — the current H2 config stays for local dev (`dev` profile), and a new `prod` profile points to Supabase. No Java code changes are needed.

---

## Steps

### 1. Create a Supabase project
- Go to [supabase.com](https://supabase.com) → New Project → choose a region close to you → set a strong database password (save it).
- Once created, go to **Project Settings → Database** → copy the **Connection string (URI)** under "JDBC". It looks like:
  `jdbc:postgresql://db.<ref>.supabase.co:5432/postgres`

### 2. Add PostgreSQL JDBC driver to `pom.xml`
- Add `org.postgresql:postgresql` as a `runtime` dependency (Spring Boot BOM manages the version).
- Keep the existing `com.h2database:h2` `runtime` dependency — it's harmless and used only when the `dev` profile is active.

### 3. Split `application.yaml` into 3 files
- `application.yaml` — shared config only (app name, JSP view resolver, JPA `show-sql`). Remove datasource + H2 config from here.
- `application-dev.yaml` (new) — H2 datasource, `ddl-auto: create-drop`, H2 console enabled. Active by default locally.
- `application-prod.yaml` (new) — Supabase PostgreSQL JDBC URL, username `postgres`, password (see step 4), `ddl-auto: update`, H2 console disabled.

### 4. Secure Supabase credentials
- The password is **never hardcoded** in yaml files.
- `application-prod.yaml` will reference environment variables:
  ```
  spring.datasource.password: ${SUPABASE_PASSWORD}
  ```
- Set `SUPABASE_PASSWORD` as an OS environment variable or in your IDE run config when running with the prod profile.

### 5. Activate the prod profile when deploying
- Locally: run with default (no profile needed → uses `dev`).
- To test prod locally: pass `-Dspring.profiles.active=prod` to the run command, with the env var set.
- When deploying: set `SPRING_PROFILES_ACTIVE=prod` and `SUPABASE_PASSWORD=...` on the server.

### 6. Verify `User` entity compatibility with PostgreSQL
- `User.java` uses `@Table(name = "users")` and `GenerationType.IDENTITY` — both are fully compatible with PostgreSQL. No changes needed.
- Hibernate with `ddl-auto: update` will auto-create the `users` table on the Supabase database on first startup.

---

## Verification
1. `mvnw spring-boot:run` (no flag) → H2 still works, `http://localhost:8080` unchanged.
2. `mvnw spring-boot:run -Dspring.profiles.active=prod` (with `SUPABASE_PASSWORD` set) → app starts, Hibernate runs `create table users ...` on Supabase, CRUD at `/users` persists to Supabase.
3. Check the **Supabase Table Editor** in the dashboard — the `users` table should appear after first run.

---

## Decisions
- **Profiles over a single yaml**: avoids credential leaks and lets you switch databases without code changes.
- **`ddl-auto: update` on prod**: lets Hibernate create the table automatically on first run; safe for a new empty database. Can be changed to `validate` later once schema is stable.
- **Environment variable for password**: never storing credentials in version-controlled files.
