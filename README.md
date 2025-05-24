# 🧠 Stalking Case Similarity Finder (RAG 기반 법률 질의 응답 시스템)

이 프로젝트는 사용자의 질문을 받아, 벡터 DB(Qdrant)에 저장된 스토킹 관련 판례들 중 유사한 사례를 검색하고, 이를 기반으로 GPT를 활용하여 법률적 답변을 생성하는 **법률 상담형 RAG(검색-생성) 시스템**입니다.

---

## 📌 주요 기능

- ✅ 사용자의 질문을 OpenAI Embedding API를 통해 벡터화
- ✅ Qdrant에 저장된 판례 벡터들과 유사도 기반 검색
- ✅ 검색된 유사 판례들을 GPT 프롬프트에 포함해 요약 및 법률적 판단 제공
- ✅ REST API(`/search`)로 질문 → 결과 반환까지 처리

---

## 🧰 사용 기술

| 기술               | 설명 |
|--------------------|------|
| **Java 21**        | 백엔드 언어 |
| **Spring Boot**    | 백엔드 프레임워크 |
| **WebClient**      | 외부 API 호출(OpenAI, Qdrant) |
| **OpenAI API**     | 질문 및 판례 Embedding 생성 (`text-embedding-ada-002`) |
| **Qdrant**         | 벡터 DB로 판례 벡터 저장 및 유사도 검색 |
| **GPT-3.5-turbo**  | 유사 판례 기반 답변 생성 (향후 프롬프트 연동 예정) |

---

## ⚙️ 시스템 구조

