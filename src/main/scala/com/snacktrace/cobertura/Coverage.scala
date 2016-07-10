package com.snacktrace.cobertura

/**
  * Created by ultmast on 9/07/16.
  */
final case class Coverage(sources: Option[Sources], packages: Packages, lineCoverage: LineRate,
  branchCoverage: BranchRate, linesCovered: LinesCovered, linesTotal: LinesTotal,
  branchesCovered: BranchesCovered, branchesTotal: BranchesTotal, complexity: Complexity,
  version: Version, timestamp: Timestamp)

final case class Complexity(value: Double) extends AnyVal

final case class Source(value: String) extends AnyVal

final case class Sources(value: Seq[Source]) extends AnyVal

final case class Packages(value: Seq[Package]) extends AnyVal

final case class PackageName(value: String) extends AnyVal

final case class Version(value: String) extends AnyVal

final case class Timestamp(value: Long) extends AnyVal

final case class LineRate(value: Float) extends AnyVal

final case class LinesCovered(value: Int) extends AnyVal

final case class LinesTotal(value: Int) extends AnyVal

final case class BranchRate(value: Float) extends AnyVal

final case class BranchesCovered(value: Int) extends AnyVal

final case class BranchesTotal(value: Int) extends AnyVal

final case class Package(classes: Classes, name: PackageName, lineRate: LineRate, branchRate: BranchRate,
  complexity: Complexity)

final case class ClassName(value: String) extends AnyVal

final case class Classes(value: Seq[Class]) extends AnyVal

final case class Class(methods: Methods, lines: Lines, name: ClassName, fileName: FileName, lineRate: LineRate,
  branchRate: BranchRate, complexity: Complexity)

final case class FileName(value: String) extends AnyVal

final case class Methods(value: Seq[Method]) extends AnyVal

final case class Lines(value: Seq[Line]) extends AnyVal

final case class Method(lines: Lines, name: MethodName, signature: MethodSignature, lineRate: LineRate,
  branchRate: BranchRate)

final case class MethodName(value: String) extends AnyVal

final case class MethodSignature(value: String) extends AnyVal

final case class Line(conditions: Option[Conditions], number: LineNumber, hits: LineHits, isBranch: IsBranch,
  conditionCoverage: Option[ConditionCoverage])

final case class ConditionCoverage(value: String) extends AnyVal

final case class LineNumber(value: Int) extends AnyVal

final case class LineHits(value: Int) extends AnyVal

final case class IsBranch(value: Boolean) extends AnyVal

final case class Conditions(value: Seq[Condition]) extends AnyVal

final case class Condition(number: ConditionNumber, `type`: ConditionType, coverage: ConditionCoverage)

final case class ConditionNumber(value: Int) extends AnyVal

final case class ConditionType(value: String) extends AnyVal