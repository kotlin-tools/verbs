# Contributing to Verbs

Thank you for your interest in contributing to the Verbs library! This document provides guidelines and information for contributors.

## Getting Started

### Prerequisites

- JDK 17 or higher
- Gradle 8.0 or higher
- Git

### Setting up the development environment

1. Fork the repository
2. Clone your fork:
   ```bash
   git clone https://github.com/your-username/verbs.git
   cd verbs
   ```
3. Build the project:
   ```bash
   ./gradlew build
   ```
4. Run tests:
   ```bash
   ./gradlew test
   ```

## Development Workflow

### Making Changes

1. Create a new branch for your feature/fix:
   ```bash
   git checkout -b feature/your-feature-name
   ```
2. Make your changes
3. Add tests for new functionality
4. Ensure all tests pass:
   ```bash
   ./gradlew test
   ```
5. Check code coverage:
   ```bash
   ./gradlew jacocoTestReport
   ```
6. Commit your changes with a descriptive message:
   ```bash
   git commit -m "feat: add new conjugation feature"
   ```

### Code Style

- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Keep functions small and focused
- Write comprehensive tests

### Testing

- Write unit tests for all new functionality
- Ensure test coverage is above 80%
- Use descriptive test names
- Test both positive and negative cases

### Commit Messages

Follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

- `feat:` for new features
- `fix:` for bug fixes
- `docs:` for documentation changes
- `style:` for formatting changes
- `refactor:` for code refactoring
- `test:` for adding or updating tests
- `chore:` for maintenance tasks

## Pull Request Process

1. Update the README.md with details of changes if applicable
2. Update the CHANGELOG.md with a summary of changes
3. Ensure the build passes and all tests are green
4. Request review from maintainers

## Reporting Issues

Before creating an issue, please:

1. Check if the issue has already been reported
2. Use the provided issue templates
3. Include a minimal code example that reproduces the issue
4. Specify your environment (OS, JDK version, etc.)

## Feature Requests

When suggesting new features:

1. Explain the use case and motivation
2. Provide code examples of how you'd like to use the feature
3. Consider backward compatibility
4. Discuss potential implementation approaches

## Release Process

Releases are automated via GitHub Actions:

1. Create and push a new tag:
   ```bash
   git tag v1.1.0
   git push origin v1.1.0
   ```
2. The release workflow will automatically:
   - Build and test the project
   - Generate a changelog
   - Create a GitHub release
   - Publish to GitHub Packages

## Questions?

If you have questions about contributing, please:

1. Check the existing issues and discussions
2. Create a new issue with the "question" label
3. Reach out to the maintainers

Thank you for contributing to Verbs! 🚀 