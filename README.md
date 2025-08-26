# 🎬 Movie Ticket Booking Platform

A full‑stack Movie Ticket Booking Platform built with **Spring Boot**, **React**, **Razorpay**, and **AWS S3**. It supports three roles — **Admin**, **Theatre Owner**, and **User** — and now includes **🎥 Cinemate AI** powered by **Google Gemini** for movie discovery, support, and smart search.

---

## 🧩 Features

### 👥 Role‑Based Access

**Admin**

* Manage users, theatre owners, movies, screens & shows
* View payment reports and overall system activity

**Theatre Owner**

* Create and manage theatres, screens, and movie shows
* Monitor bookings and revenue

**User**

* Browse movies by location and date
* View available seats and book tickets
* Secure checkout via Razorpay

### 💳 Razorpay Integration

* Secure online payments using Razorpay Checkout
* Payment status & history tracked per transaction
* Webhook support for reliable payment confirmation

### ☁️ AWS S3 Integration

* Posters, stills, and theatre images stored on S3
* Pre‑signed uploads for performant, safe media flow

### 🪑 Seat Booking & Management

* Real‑time seat status (available / booked)
* Temporary seat blocking during checkout (extensible with WebSockets)

### 🤖 Cinemate AI (Gemini‑powered)

* **Movie Q\&A & Discovery**: Ask about cast, genres, ratings, and similar movies
* **Smart Search**: Natural‑language search across movies, theatres, and locations
* **Booking Helper**: Guides users through showtimes, seat selection, and payments
* **Customer Support Lite**: Answers common queries (refund policy, reschedule, etc.)
* **Safety & Guardrails**: Domain‑restricted prompts; polite fallbacks for off‑topic

---

## ⚙️ Tech Stack

### Backend

* Java 17+, Spring Boot (Web, Security, Validation)
* JPA/Hibernate
* MySQL
* **Gemini Integration** via Google Generative AI (server‑side proxy)

### Frontend

* React.js + Vite
* Redux Toolkit
* Axios
* Bootstrap + custom CSS

### DevOps & Cloud

* AWS S3 (media storage)
* Razorpay (payments)
* GitHub (version control)
* Maven (build tool)

---

## 🏗️ Architecture (High‑Level)

```
[React SPA] ── Axios ──▶ [Spring Boot API]
   │                         │
   │                         ├── MySQL (JPA)
   │                         ├── Razorpay (orders/webhooks)
   │                         ├── AWS S3 (media, pre‑signed URLs)
   │                         └── Gemini (Cinemate AI via server‑side proxy)
```

---

## 📦 Folder Structure

```
backend/
├─ src/main/java/com/ramakrishna/moviebooking/
│  ├─ controllers/
│  ├─ services/
│  ├─ models/
│  └─ repositories/

```

## 🧠 Cinemate AI — Implementation Notes

### Why server‑side Gemini?

* Hides API keys, controls rate limits, adds safety filters
* Can enrich prompts with live system data (movies, shows, seats)

### Spring Boot Service (pseudo‑flow)

1. Receive user message at `/api/ai/chat`.
2. Classify intent (simple rules + Gemini prompt) — discover / support / booking.
3. If needed, query DB (e.g., upcoming shows in city/date) and compose a grounded context.
4. Call Gemini with a **domain‑constrained** system prompt.
5. Return structured reply + optional `data` for the UI.

**System Prompt (sketch)**

* You are *Cinemate AI*, an assistant for a movie booking app.
* Only answer *movie‑domain* questions (movies, theatres, shows, seats, payments policy). For anything else, respond with: “I can help with movies and bookings only.”
* Prefer concise, actionable answers. When asked for bookings, request city/date if missing.



## 🔐 Payments — Best Practices

* Verify Razorpay signature server‑side before marking bookings as paid
* Use idempotency keys for `verify` to avoid double updates
* Persist **PaymentIntent → Booking** mapping; only confirm seats on paid status

---

## 📌 Future Enhancements

* 📱 Full mobile‑first responsive UI
* 📊 Admin analytics (top movies, occupancy, revenue trends)
* 📧 Email/SMS ticket confirmations & e‑tickets (QR)
* 🧾 GST invoice download
* 🗣️ Cinemate AI: voice input & multilingual, RAG with FAQs/policies
* 🔔 WebSocket seat blocking + live seat maps

---

## 🧑‍💻 Author

**Mandalaneni Sai Balaji Siva Rama Krishna**
📧 [sivaramm683@gmail.com](mailto:sivaramm683@gmail.com)
🔗 LinkedIn: [https://www.linkedin.com/in/siva-rama-krishna-mandalaneni](https://www.linkedin.com/in/siva-rama-krishna-mandalaneni)
