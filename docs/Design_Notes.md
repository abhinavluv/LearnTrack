# Design Notes

## Why ArrayList

The repositories use `ArrayList` to store students, courses, and enrollments in memory. An `ArrayList` can grow as records are added, unlike an array with a fixed size.

## Static Members

`IdGenerator` uses static counters and methods to provide the next student, course, and enrollment IDs. The counters belong to the utility class and are shared across its use in the application.

## Inheritance

`Student` extends `Person`. The shared `id`, `firstName`, `lastName`, and `email` fields and accessors are defined once in `Person`; `Student` adds `batch` and `active`. `Student` calls the `Person` constructor with `super` and overrides `getDisplayName()` to include its batch.