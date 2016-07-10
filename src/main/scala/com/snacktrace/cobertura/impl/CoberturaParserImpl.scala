package com.snacktrace.cobertura.impl

import java.io.InputStream

import com.snacktrace.cobertura._

/**
  * Created by ultmast on 9/07/16.
  */
class CoberturaParserImpl extends CoberturaParser {
  override def parse(stream: InputStream): Coverage = {
    val factory = javax.xml.parsers.SAXParserFactory.newInstance()
    factory.setValidating(false)
    val parser = factory.newSAXParser()
    val root = xml.XML.withSAXParser(parser).load(stream)
    try {
      val lineRate = LineRate(root.attr("line-rate").toFloat)
      val branchRate = BranchRate(root.attr("branch-rate").toFloat)
      val linesCovered = LinesCovered(root.attr("lines-covered").toInt)
      val linesTotal = LinesTotal(root.attr("lines-valid").toInt)
      val branchesCovered = BranchesCovered(root.attr("branches-covered").toInt)
      val branchesTotal = BranchesTotal(root.attr("branches-valid").toInt)
      val complexity = Complexity(root.attr("complexity").toDouble)
      val version = Version(root.attr("version"))
      val timestamp = Timestamp(root.attr("timestamp").toLong)

      val sourcesNode = root.child.find(n => n.label == "sources")
      val sources = sourcesNode.map { sourcesNode =>
        Sources(sourcesNode.child.map(_.text).map(Source))
      }

      val packagesNode = root.child.find(n => n.label == "packages").get
      val packages = Packages(packagesNode.child.map(parsePackage))

      Coverage(sources, packages, lineRate, branchRate, linesCovered, linesTotal, branchesCovered, branchesTotal,
        complexity, version, timestamp)
    } catch {
      case throwable: Throwable =>
        throw new CoberturaParserException(s"Exception parsing node ${root} as a coverage", Some(throwable))
    }
  }


  private def parsePackage(packageNode: Node): Package = try {
    val name = PackageName(packageNode.attr("name"))
    val lineRate = LineRate(packageNode.attr("line-rate").toFloat)
    val branchRate = BranchRate(packageNode.attr("branch-rate").toFloat)
    val complexity = Complexity(packageNode.attr("complexity").toDouble)

    val classes = Classes(packageNode.child.head.child.map(parseClass))

    Package(classes, name, lineRate, branchRate, complexity)
  } catch {
    case throwable: Throwable =>
      throw new CoberturaParserException(s"Exception parsing node ${packageNode} as a package", Some(throwable))
  }

  private def parseClass(classNode: Node): Class = try {
    val name = ClassName(classNode.attr("name"))
    val fileName = FileName(classNode.attr("filename"))
    val lineRate = LineRate(classNode.attr("line-rate").toFloat)
    val branchRate = BranchRate(classNode.attr("branch-rate").toFloat)
    val complexity = Complexity(classNode.attr("complexity").toDouble)

    val methods = Methods(classNode.child.find(_.label == "methods").get.child.map(parseMethod))

    val lines = Lines(classNode.child.find(_.label == "lines").get.child.map(parseLine))

    Class(methods, lines, name, fileName, lineRate, branchRate, complexity)
  } catch {
    case throwable: Throwable =>
      throw new CoberturaParserException(s"Exception parsing node ${classNode} as a class", Some(throwable))
  }

  private def parseMethod(methodNode: Node): Method = try {
    val name = MethodName(methodNode.attr("name"))
    val signature = MethodSignature(methodNode.attr("signature"))
    val lineRate = LineRate(methodNode.attr("line-rate").toFloat)
    val branchRate = BranchRate(methodNode.attr("branch-rate").toFloat)

    val lines = Lines(methodNode.child.head.child.map(parseLine))

    Method(lines, name, signature, lineRate, branchRate)
  } catch {
    case throwable: Throwable =>
      throw new CoberturaParserException(s"Exception parsing node ${methodNode} as a method", Some(throwable))
  }

  private def parseCondition(conditionNode: Node): Condition = try {
    val number = ConditionNumber(conditionNode.attr("number").toInt)
    val `type` = ConditionType(conditionNode.attr("type"))
    val coverage = ConditionCoverage(conditionNode.attr("coverage"))
    Condition(number, `type`, coverage)
  } catch {
    case throwable: Throwable =>
      throw new CoberturaParserException(s"Exception parsing node ${conditionNode} as a condition", Some(throwable))
  }

  private def parseLine(lineNode: Node): Line = try {
    val number = LineNumber(lineNode.attr("number").toInt)
    val hits = LineHits(lineNode.attr("hits").toInt)
    val isBranch = IsBranch(lineNode.attr("branch").toBoolean)
    val conditionCoverage = lineNode.attribute("condition-coverage").flatMap(_.headOption).map(_.text).map(ConditionCoverage)

    val conditions = lineNode.child.headOption.map(_.child.map(parseCondition)).map(Conditions)

    Line(conditions, number, hits, isBranch, conditionCoverage)
  } catch {
    case throwable: Throwable =>
      throw new CoberturaParserException(s"Exception parsing node ${lineNode} as a line", Some(throwable))
  }

  implicit class PimpedNode(node: Node) {
    def attr(name: String): String = try {
      node.attribute(name).flatMap(_.headOption).get.text
    } catch {
      case throwable: Throwable =>
        throw new CoberturaParserException(s"Expected attribute ${name} in node ${node}", Some(throwable))
    }
  }
}