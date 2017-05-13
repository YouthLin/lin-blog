#!/bin/bash

# nplurals=2; plural=n==1?0:1;

base=$(cd "$(dirname "$0")"; pwd)
#echo ${base}
# msgfmt
# --java2 表示使用 Java 1.2+ 语法，
# -d 表示目录
# -r 表示要生成的资源的 basename
# -r 表示 Locale
# 最后是 po 文件

PREFIX=

# Mac 安装 Poedit 后会自带 gettext 工具
PREFIX=/Applications/Poedit.app/Contents/PlugIns/GettextTools.bundle/Contents/MacOS/bin/

${PREFIX}msgfmt --java2 -d ${base} -r Blog -l zh_CN ${base}/Blog.po > /dev/null 2>&1
