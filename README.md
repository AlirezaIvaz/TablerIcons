# TablerIcons for Android

[![](https://jitpack.io/v/ir.alirezaivaz/tablericons.svg)](https://jitpack.io/#ir.alirezaivaz/tablericons)
[![Apache2](http://img.shields.io/badge/license-APACHE2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![API 21](https://img.shields.io/badge/Min%20API-21-brightgreen)](https://developer.android.com/about/versions/lollipop)
[![Kotlin 1.8.0](https://img.shields.io/badge/Kotlin-1.8.0-blueviolet)](https://kotlinlang.org)
[![Issues](https://img.shields.io/github/issues/AlirezaIvaz/TablerIcons)](https://github.com/AlirezaIvaz/TablerIcons/issues)

[TablerIcons](https://tabler-icons.io) is a set of over 4050 free MIT-licensed high-quality SVG
icons for you to use in your projects.

This library offers you the vector drawable version of these icons with some additional features that you can easily use in your Android projects.

## Adding to your project

### 1. Adding the library repository

If you're using the old project structure, add **JitPack** in your root `build.gradle` or `build.gradle.kts` file like this:

<details>
<summary><code>build.gradle</code></summary>

```groovy
allprojects {
    repositories {
        ...
        maven {
            url 'https://jitpack.io'
        }
    }
}
```

</details>

<details open>
<summary><code>build.gradle.kts</code></summary>

```kotlin
allprojects {
    repositories {
        ...
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
```

</details>

Otherwise if you're using new project structure, add **JitPack** in your `settings.gradle` or `settings.gradle.kts` like this:

<details>
<summary><code>settings.gradle</code></summary>

```groovy
dependencyResolutionManagement {
    ...
    repositories {
        ...
        maven {
            url 'https://jitpack.io'
        }
    }
}
```

</details>

<details open>
<summary><code>settings.gradle.kts</code></summary>

```kotlin
dependencyResolutionManagement {
    ...
    repositories {
        ...
        maven {
            url = uri("https://jtipack.io")
        }
    }
}
```

</details>

### 2. Adding the library dependency

Now add the library dependency in your application module `build.gradle` or `build.gradle.kts` file like this:


<details>
<summary><code>build.gradle</code></summary>

```groovy
dependencies {
    ...
    def tablerIconsVersion = "0.9.0" // You can find the latest version from releases page
    implementation "ir.alirezaivaz:tablericons:$tablerIconsVersion"
}
```

</details>

<details open>
<summary><code>build.gradle.kts</code></summary>

```kotlin
dependencies {
    ...
    val tablerIconsVersion = "0.9.0" // You can find the latest version from releases page
    implementation("ir.alirezaivaz:tablericons:$tablerIconsVersion")
}
```

</details>

## Usage

Just call the desired icon from the drawables like this:
```xml
<ImageView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/ic_arrow_back" />
```

You can see the list of icons categorized on [this page](https://tabler-icons.io).

> **Note:** The names of all icons in this library start with `ic_` and instead of `-` (dash) you should use `_` (underline).<br>
> E.g: If you want to use an icon named `arrow-back`, you should change its name to `ic_arrow_back`.

### Change the color of icons

If you want to change the color of all icons at once, override `tabler_icon` to your `colors.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    ...
    <color name="tabler_icon">#2196F3</color>
</resources>
```

Otherwise, if you want to change the color of one icon, just use `app:tint` attribute in your `ImageView` like this:

```xml
<ImageView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/ic_arrow_back"
    app:tint="#2196F3" />
```

## Licenses

```
Copyright 2017-2023 Alireza Ivaz

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

<details>
<summary><b>TablerIcons</b></summary>

```
MIT License

Copyright (c) 2020-2023 Paweł Kuna

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

</details>
