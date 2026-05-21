# Pathfinder — Adaptive Career Guidance (Spring Boot core)

A runnable foundation for the secondary-school career guidance app:
Spring Boot 3 + Thymeleaf + Spring Security + PostgreSQL.

## What's in this build (the runnable core)

- Login + registration (choose **Student** or **Counsellor**)
- Spring Security with **BCrypt** password hashing and **role-based access control**
- All core JPA entities (users, roles, profiles, RIASEC categories/questions/variants,
  responses, scores, careers, classrooms, memberships, per-category lock status)
- Counsellor: create a classroom, auto-generated join code
- Student: see classroom state, join with a code
- Light/dark theme toggle (remembered, works offline)
- A `DataSeeder` that fills roles, the 6 RIASEC categories, sample careers, and a demo
  counsellor on first run

**Demo counsellor login:** username `counsellor`, password `password123` (change it).

## What's NOT in this build yet (next phases)

The 30-question test flow, score calculation + results screen, offline sync queue
(Service Worker + idempotent upsert via `clientUuid`), the AI commentary call, the
counsellor analytics dashboard, and the question-rephrasing screen. The entities and
hooks for all of these already exist — they get layered on next.

## Run it (IntelliJ IDEA)

1. Install JDK 17+, Maven (bundled with IntelliJ), and PostgreSQL.
2. In PostgreSQL, create the database:
   ```sql
   CREATE DATABASE pathfinder;
   ```
3. Open `src/main/resources/application.yml` and set your real DB password.
4. Open the project in IntelliJ (it auto-detects Maven). Let it download dependencies.
5. Run `PathfinderApplication`.
6. Visit http://localhost:8080 — you'll land on the login page.

Tables are created automatically (`ddl-auto: update`) and seed data loads on first run.

## Adding the Gemini AI later

The key lives **only on the backend** — never in browser JavaScript. It's already wired
into `application.yml` under `pathfinder.ai.gemini`. When you build the AI step:

1. Put your key in `application.yml` (`api-key`) and set `enabled: true`.
2. Make the HTTPS call to Gemini from a Spring `@Service` using Spring's `RestClient`.
3. The browser calls *your* endpoint; your server calls Gemini; only the text comes back.
4. Add a fallback message for when the free daily limit is hit, so the app degrades gracefully.

## Push to GitHub (secrets stay out)

`application.yml` is in `.gitignore` so your DB password and API key never get pushed.
The committed `application-example.yml` documents the required keys without secrets.

```bash
git init
git add .
git commit -m "Pathfinder core: auth, security, classrooms"
git branch -M main
git remote add origin https://github.com/<your-username>/pathfinder.git
git push -u origin main
```

Double-check before pushing:
```bash
git status        # application.yml must NOT appear in the list
```

## Deployment note (important)

**Netlify cannot host this app.** Netlify only serves static files; Spring Boot is a Java
server that must stay running. Use **Render** (free web service) for the Spring Boot app
and a managed **PostgreSQL** (Render or Railway free tier) for the database. You deploy by
connecting your GitHub repo to Render and setting the same config values as environment
variables instead of editing `application.yml` on the server.
