# reload-error-boot

Minimal repo for clojure-emacs/cider#2104.

This shows a problem with reloading the `clojure.test.check.generators` namespace without reloading the `clojure.spec.gen.alpha` namespace.

Since `clojure.spec.gen.alpha` [doesn't `:require`](https://github.com/clojure/spec.alpha/blob/spec.alpha-0.1.143/src/main/clojure/clojure/spec/gen/alpha.clj#L9-L11) `clojure.test.check.generators`, but rather dynamically loads some of its vars, the reload dependency tracking doesn't work properly.

## Usage

1. `cider-jack-in`
2. Go to `core.cljc` and `cider-eval-buffer`, `cider-refresh`, etc.
   - See that generators for `clojure.spec.gen.alpha` and `clojure.test.check.generators` have the same classloaders:

   ```
   clojure.spec.alpha generator's classloader:  #object[clojure.lang.DynamicClassLoader 0x73867ca9 clojure.lang.DynamicClassLoader@73867ca9]
   clojure.test.check.generators generator's classloader:  #object[clojure.lang.DynamicClassLoader 0x73867ca9 clojure.lang.DynamicClassLoader@73867ca9]
   ```

3. In the REPL, compile ClojureScript with `(boot (cljs))`
4. `C-u cider-refresh` (`refresh-all`), then `cider-eval-buffer` again
   - See that the generators now have different classloaders:

   ```
   clojure.spec.alpha generator's classloader:  #object[clojure.lang.DynamicClassLoader 0x73867ca9 clojure.lang.DynamicClassLoader@73867ca9]
   clojure.test.check.generators generator's classloader:  #object[clojure.lang.DynamicClassLoader 0x170397bd clojure.lang.DynamicClassLoader@170397bd]
   ```

   In particular, that of `clojure.test.check.generators` has changed, while that of `clojure.spec.gen.alpha` ha  remained the same. This is because `clojure.test.check.generators` was reloaded by the `cider-refresh`, thereby redefining the `Generator` record, but `clojure.spec.gen.alpha` was not.

   For more on records and classloaders, see [Chris Houser's StackOverflow explanation](https://stackoverflow.com/a/7473707/864684).
5. Go to `clojure/spec/gen/alpha.clj` and eval the `lazy-combinators` form

   ```clojure
   (lazy-combinators hash-map list map not-empty set vector vector-distinct fmap elements
                     bind choose fmap one-of such-that tuple sample return
                     large-integer* double* frequency)
   ```
6. `cider-eval-buffer` and see that `clojure.spec.gen.alpha`'s generator now uses the `clojure.test.check.generators`'s classloader again:

   ```
   clojure.spec.alpha generator's classloader:  #object[clojure.lang.DynamicClassLoader 0x170397bd clojure.lang.DynamicClassLoader@170397bd]
   clojure.test.check.generators generator's classloader:  #object[clojure.lang.DynamicClassLoader 0x170397bd clojure.lang.DynamicClassLoader@170397bd]
   ```

## License

Copyright © 2017 Tianxiang Xiong

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
