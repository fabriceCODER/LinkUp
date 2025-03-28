# AI-Powered Job Recruitment Platform

## Overview
This project is a **full-stack AI-powered job recruitment platform** built using **Spring Boot (backend)** and **Next.js (frontend)**. The system provides **AI-based job matching**, secure authentication with **Spring Security & JWT**, and role-based access control.

## Features
### **Authentication & Authorization**
- **JWT & Spring Security** for secure authentication
- **OAuth2 Login (Google, LinkedIn)**
- **Role-Based Access Control (RBAC)**:
  - **Admin**: Manages users, jobs, AI algorithms
  - **Employer**: Posts jobs, manages applications
  - **Candidate**: Applies for jobs, uploads resumes

### **Candidate Features**
- Create an account & manage profile
- Upload resume (AI-powered parsing)
- Browse & apply for jobs
- AI-powered job recommendations
- Job application tracking

### **Employer Features**
- Post job listings & manage applications
- AI-powered candidate recommendations
- Interview scheduling system
- Analytics dashboard

### **Admin Features**
- Manage users, employers, and candidates
- View platform analytics
- Manage AI job-matching algorithms

### **AI Integration**
- **AI-powered job matching** (NLP & ML)
- **Resume parsing & scoring** (TF-IDF or OpenAI embeddings)
- **Chatbot for job assistance** (Optional)

## **Tech Stack**
### **Backend (Spring Boot 3+)**
- **Spring Boot** (RESTful APIs, MVC)
- **Spring Security & JWT** (Authentication & Authorization)
- **Spring Data JPA & Hibernate** (Database ORM)
- **PostgreSQL** (Relational Database)
- **Lombok** (Simplified model classes)
- **TensorFlow (Optional AI integration)**

### **Frontend (Next.js 14+)**
- **Next.js (App Router)**
- **Redux Toolkit (State Management)**
- **TypeScript**
- **Shadcn/UI & Tailwind CSS (Modern UI)**
- **Framer Motion (Animations)**
- **React Query (API Calls & Caching)**

## **Project Setup**
### **Backend Setup (Spring Boot)**
1. Clone the repository:
   ```sh
   git clone https://github.com/fabriceCODER/LinkUp/job-platform.git
   cd job-platform/backend
   ```
2. Configure `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/jobplatform
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   app.jwt.secret=your_secret_key
   ```
3. Run the backend:
   ```sh
   mvn spring-boot:run
   ```

### **Frontend Setup (Next.js)**
1. Move to the frontend directory:
   ```sh
   cd job-platform/frontend
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Run the development server:
   ```sh
   npm run dev
   ```

## **API Endpoints**
### **Authentication**
- `POST /api/auth/login` → User login (JWT-based authentication)
- `POST /api/auth/register` → New user registration

### **Jobs**
- `GET /api/jobs` → Fetch all job listings
- `POST /api/jobs` → Create a job (Employer only)

### **AI-Powered Job Matching**
- `POST /api/resume/match` → Get job recommendations based on resume text

## **Deployment & DevOps**
- **Backend:** Docker + Kubernetes (K8s)
- **Frontend:** Vercel (Next.js Deployment)
- **Database:** PostgreSQL on AWS RDS
- **CI/CD:** GitHub Actions (Automated builds & tests)

## **Contributing**
1. Fork the repository
2. Create a new branch (`feature/new-feature`)
3. Commit your changes (`git commit -m "Add new feature"`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Open a Pull Request

## **License**
This project is licensed under the MIT License.

