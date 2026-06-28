# Week2 - 대규모 데이터 파이프라인 & 병렬 처리 최적화

## 프로젝트 목표

AI 채팅 서비스를 가정하여 멀티스레드 환경에서 발생하는 동시성 문제를 학습하고, Queue 기반 비동기 처리 파이프라인을 구현한다.

이번 스터디에서는 다음 세 가지 미션을 수행하였다.

* Mission 0 : AI 요청 스케줄링 큐 구현
* Mission 1 : Thread-Per-Connection 구조 분석 및 스레드 병목 실습
* Mission 2 : Queue 기반 AI 채팅 파이프라인 구현

---

# Mission 0

## 주제

고동시성 환경에서의 AI 요청 비동기 스케줄링 큐 구현

## 목표

외부 AI API의 Rate Limit이 존재한다고 가정하고, 요청의 우선순위와 재시도를 고려한 Scheduler를 구현한다.

---

## 프로젝트 구조

```
Request

↓

PriorityBlockingQueue

↓

Scheduler

↓

Worker
```

---

## 구현 내용

### Request

AI 요청을 표현하는 객체

```
requestId
userId
prompt
priority
retryCount
createdAt
```

* requestId : 요청 식별자
* userId : 요청 사용자
* prompt : AI에게 전달할 질문
* priority : 우선순위
* retryCount : 재시도 횟수
* createdAt : 요청 생성 시간

---

### Scheduler

Scheduler는 PriorityBlockingQueue를 관리한다.

주요 기능

* 요청 등록
* 요청 조회
* 실패 요청 재등록
* Exponential Backoff 계산

PriorityBlockingQueue를 이용하여

1. 높은 Priority
2. 동일 Priority인 경우 먼저 생성된 Request

순으로 처리하도록 구현하였다.

---

### PriorityComparator

PriorityBlockingQueue의 우선순위를 결정하는 Comparator를 직접 구현하였다.

정렬 기준

```
Priority DESC

↓

CreatedAt ASC
```

---

### Worker

Worker는 Runnable을 구현하여 ExecutorService에서 실행된다.

동작 순서

```
Queue

↓

Request 조회

↓

Fake AI API 호출

↓

성공

또는

실패 → Scheduler.retry()
```

---

### Exponential Backoff

재시도 횟수에 따라

```
1초

↓

2초

↓

4초

↓

8초
```

와 같이 대기 시간이 증가하도록 구현하였다.

---

## 사용 기술

* PriorityBlockingQueue
* Comparator
* Runnable
* ExecutorService
* Exponential Backoff
* Producer-Consumer Pattern

---

## 구현 결과

* Priority 기반 요청 처리 확인
* Exponential Backoff 재시도 확인
* ExecutorService를 이용한 Worker 실행 확인

---

# Mission 1

## 주제

Thread-Per-Connection 소켓 서버 분석 및 Thread Dump 실습

## 목표

Thread-Per-Connection 구조에서 발생하는 Thread 증가와 병목 현상을 분석한다.

---

## 프로젝트 구조

```
ClientMain

↓

SocketServer

↓

accept()

↓

ClientHandler(Thread)
```

---

## 구현 내용

### SocketServer

ServerSocket을 생성하고

Client가 접속할 때마다

```
new Thread(new ClientHandler(...)).start()
```

를 수행하여 Thread-Per-Connection 구조를 구현하였다.

---

### ClientMain

다수의 Client를 생성하여

동시에 Socket 연결을 수행하도록 구현하였다.

---

### ClientHandler

각 Client의 Socket을 일정 시간 유지하도록 구현하여

동시에 많은 Thread가 살아있는 환경을 재현하였다.

---

## 학습 내용

Thread-Per-Connection 구조에서는

Client 수가 증가할수록

Server Thread 역시 증가한다.

```
100 Client

↓

100 Thread

↓

1000 Client

↓

1000 Thread
```

이와 같은 구조는

메모리 사용량 증가와 Context Switching 증가를 유발한다.

---

## 사용 기술

* ServerSocket
* Socket
* Thread
* Runnable

---

## 구현 결과

* Thread-Per-Connection 구조 구현
* 다수 Client 동시 연결 확인
* Thread 증가 현상 재현

---

## 아쉬운 점

과제에서 요구한

* synchronized Lock 병목
* jstack 분석

은 일부 구현 및 분석만 수행하였다.

향후

* synchronized 병목 실험
* Thread Dump 분석
* VisualVM Profiling

을 추가로 진행할 예정이다.

---

# Mission 2

## 주제

Queue 기반 AI 채팅 이력 파이프라인 구현

## 목표

Producer-Consumer 구조를 이용하여

비동기 AI 채팅 파이프라인을 구현한다.

---

## 프로젝트 구조

```
Client

↓

ClientHandler

↓

MessageQueue

↓

MessageWorker

↓

AIEngine

↓

BroadcastManager

↓

ChatLogWriter
```

---

## 패키지 구조

```
mission2

main

server

scheduler

engine

factory

session

model

log
```

---

## 구현 내용

### SessionManager

ConcurrentHashMap을 이용하여

사용자별 ChatSession을 관리하였다.

```
userId

↓

ChatSession
```

---

### ChatSession

사용자 한 명의 채팅 상태를 관리한다.

```
userId

Socket

ChatMessage List
```

---

### MessageQueue

BlockingQueue를 이용하여

Producer-Consumer 구조를 구현하였다.

Producer

* ClientHandler

Consumer

* MessageWorker

---

### MessageWorker

Queue에서 메시지를 가져와

```
AI 호출

↓

Broadcast

↓

Log 저장
```

순으로 처리하도록 구현하였다.

---

### AIEngine

기존 AI Chat 프로젝트의

Ollama Engine을 재사용하였다.

---

### BroadcastManager

AI 응답을

현재 연결된 Session 전체에게 전달하도록 구현하였다.

---

### ChatLogWriter

BufferedOutputStream을 이용하여

AI 응답을

```
chat.log
```

파일에 기록하였다.

System Call 횟수를 줄이기 위해

BufferedOutputStream을 사용하였다.

---

## 사용 기술

* ConcurrentHashMap
* BlockingQueue
* ExecutorService
* Runnable
* BufferedOutputStream
* Socket Programming
* Producer-Consumer Pattern

---

## 구현 결과

* Queue 기반 비동기 처리 구현
* AI Engine 호출 확인
* chat.log 생성 확인
* BufferedOutputStream을 이용한 로그 저장 확인

---

## 아쉬운 점

Broadcast 기능이 완전히 정상 동작하지 않아

다중 Client 환경에서의 채팅 기능은 완성하지 못하였다.

향후 개선 사항

* Broadcast 기능 개선
* ChatSession 기반 Context Memory 구현
* AI Prompt에 대화 이력 추가
* Worker Pool 최적화
* Graceful Shutdown 구현
* 비동기 로그 Flush 구현

---

# 학습 내용 정리

이번 미션을 통해 다음 내용을 학습하였다.

* PriorityBlockingQueue를 이용한 요청 스케줄링
* Exponential Backoff 알고리즘
* Producer-Consumer 패턴
* ExecutorService를 이용한 Worker 관리
* Thread-Per-Connection 구조의 한계
* ConcurrentHashMap을 이용한 Thread-safe Session 관리
* BlockingQueue를 이용한 비동기 처리
* BufferedOutputStream을 이용한 파일 I/O 최적화
* AI 채팅 파이프라인 설계
