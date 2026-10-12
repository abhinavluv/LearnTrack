# LearnTrack

LearnTrack is a Core Java console application for managing students, courses, and enrollments. It uses object-oriented programming, `ArrayList` collections, basic exception handling, and an in-memory repository layer.

## Class Diagram

```mermaid
classDiagram
	class Main
	class Person {
		-int id
		-String firstName
		-String lastName
		-String email
		+getDisplayName() String
	}
	class Student {
		-String batch
		-boolean active
		+getDisplayName() String
	}
	class Course {
		-int id
		-String courseName
		-String description
		-int durationInWeeks
		-boolean active
	}
	class Enrollment {
		-int id
		-int studentId
		-int courseId
		-LocalDate enrollmentDate
		-EnrollmentStatus status
	}
	class EnrollmentStatus {
		<<enumeration>>
		ACTIVE
		COMPLETED
		CANCELLED
	}
	class StudentService
	class CourseService
	class EnrollmentService
	class StudentRepository
	class CourseRepository
	class EnrollmentRepository
	class InputValidator
	class IdGenerator
	class EntityNotFoundException

	Person <|-- Student
	Main --> StudentService
	Main --> CourseService
	Main --> EnrollmentService
	Main --> InputValidator
	StudentService --> StudentRepository
	CourseService --> CourseRepository
	EnrollmentService --> EnrollmentRepository
	EnrollmentService --> StudentRepository
	EnrollmentService --> CourseRepository
	StudentRepository o-- Student : stores
	CourseRepository o-- Course : stores
	EnrollmentRepository o-- Enrollment : stores
	Enrollment --> EnrollmentStatus
	StudentService ..> IdGenerator
	CourseService ..> IdGenerator
	EnrollmentService ..> IdGenerator
	StudentService ..> EntityNotFoundException
	CourseService ..> EntityNotFoundException
	EnrollmentService ..> EntityNotFoundException
```

## Compile and Run

Use JDK 21 or a compatible later JDK. From the project directory, compile the Java source files.

PowerShell:

```powershell
New-Item -ItemType Directory -Force bin | Out-Null
$sources = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
javac -d bin $sources
```

macOS/Linux:

```sh
mkdir -p bin
javac -d bin $(find src -name '*.java')
```

Run the application from the project directory:

```sh
java -cp bin com.airtribe.learntrack.Main
```

## Documentation

- [Setup instructions](docs/Setup_Instructions.md)
- [JVM basics](docs/JVM_Basics.md)
- [Design notes](docs/Design_Notes.md)
