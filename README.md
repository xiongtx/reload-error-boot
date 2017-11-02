# reload-error-boot

Minimal repo for clojure-emacs/cider#2104.

This shows a problem with refreshing namespaces after compiling ClojureScript.

## Usage

1. `cider-jack-in`
2. Go to `core.cljc` and `cider-eval-buffer`, `cider-refresh`, etc.
   - See that everything works properly:

   ```
   `clojure.test.check.generators Generator?  true`
   ```

3. In the REPL, compile ClojureScript with `(boot (cljs))`
4. `C-u cider-refresh` (`refresh-all`), then `cider-eval-buffer` again
   - See that

   ```
   `clojure.test.check.generators Generator?  false`
   ```

   and the error:

   ```
   2. Unhandled clojure.lang.Compiler$CompilerException
   Error compiling src/cljc/reload_error_boot/core.cljc at (5:1)

   1. Caused by java.lang.AssertionError
   Assert failed: Second arg to such-that must be a generator (generator? gen)

   ...
   ```
5. Go to `clojure/spec/gen/alpha.clj` and eval the `lazy-combinators` form

   ```clojure
   (lazy-combinators hash-map list map not-empty set vector vector-distinct fmap elements
                  bind choose fmap one-of such-that tuple sample return
                  large-integer* double* frequency)
   ```
6. `cider-eval-buffer` and see that things are working properly again
   - `clojure.test.check.generators Generator?  true`

## License

Copyright © 2017 Tianxiang Xiong

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
