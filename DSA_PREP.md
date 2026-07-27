# DSA Prep Landing Page

This is the main entry point for interview prep in this repo.

Use this file when you want the fastest possible way to get back into DSA shape, recover pattern recall, and jump directly into solved Java references plus the matching LeetCode problems.

## At a Glance

| Item | Value |
| --- | --- |
| Pattern primers | 12 |
| Problem slots in the crash course | 69 |
| Approximate unique problems | 63 |
| Backbone | Blind 75 plus repo-specific curated gaps |
| Best use case | Fast reboot after time away from prep |
| Best cadence | 7 to 14 days for reboot, then repeat weak phases |

## How to Use This Page

1. Start with the pattern primers in Phase 0.
2. Move through the phases in order.
3. Use the Repo File URL column to jump straight into the Java file in this repo.
4. Use the LeetCode URL column to open the original problem statement.
5. Re-solve weak problems from scratch before reading your previous solution.

## Quick Navigation

- [Phase 0: Rebuild the Mental Templates](#phase-0-rebuild-the-mental-templates)
- [Phase 1: Arrays, Hashing, and String Recall](#phase-1-arrays-hashing-and-string-recall)
- [Phase 2: Two Pointers, Sliding Window, and Prefix Sum Speed](#phase-2-two-pointers-sliding-window-and-prefix-sum-speed)
- [Phase 3: Stack, Queue, Heap, and Interval Fluency](#phase-3-stack-queue-heap-and-interval-fluency)
- [Phase 4: Linked List and Binary Search Control](#phase-4-linked-list-and-binary-search-control)
- [Phase 5: Trees, BSTs, and Tries](#phase-5-trees-bsts-and-tries)
- [Phase 6: Graphs, Backtracking, and DP Recovery](#phase-6-graphs-backtracking-and-dp-recovery)
- [Phase 7: Blind 75 Consolidation Sprint](#phase-7-blind-75-consolidation-sprint)
- [Fast Restart Rules](#fast-restart-rules)
- [Weekend Shortlist](#weekend-shortlist)

## Phase 0: Rebuild the Mental Templates

Objective: refresh the shape of the core interview patterns before solving problems.

Done when: you can explain each pattern without opening the file.

| Pattern | Repo File URL |
| --- | --- |
| Two pointers | [src/main/java/com/java/poc/common_patterns/TwoPointer.java](src/main/java/com/java/poc/common_patterns/TwoPointer.java) |
| Sliding window | [src/main/java/com/java/poc/common_patterns/SlidingWindow.java](src/main/java/com/java/poc/common_patterns/SlidingWindow.java) |
| Binary search | [src/main/java/com/java/poc/common_patterns/BinarySearch.java](src/main/java/com/java/poc/common_patterns/BinarySearch.java) |
| DFS | [src/main/java/com/java/poc/common_patterns/Dfs.java](src/main/java/com/java/poc/common_patterns/Dfs.java) |
| BFS | [src/main/java/com/java/poc/common_patterns/Bfs.java](src/main/java/com/java/poc/common_patterns/Bfs.java) |
| Backtracking | [src/main/java/com/java/poc/common_patterns/Backtracking.java](src/main/java/com/java/poc/common_patterns/Backtracking.java) |
| Dynamic programming | [src/main/java/com/java/poc/common_patterns/DP.java](src/main/java/com/java/poc/common_patterns/DP.java) |
| Greedy | [src/main/java/com/java/poc/common_patterns/Greedy.java](src/main/java/com/java/poc/common_patterns/Greedy.java) |
| Kadane | [src/main/java/com/java/poc/common_patterns/Kadane.java](src/main/java/com/java/poc/common_patterns/Kadane.java) |
| Priority queues | [src/main/java/com/java/poc/common_patterns/PriorityQueues.java](src/main/java/com/java/poc/common_patterns/PriorityQueues.java) |
| Union find | [src/main/java/com/java/poc/common_patterns/UnionFind.java](src/main/java/com/java/poc/common_patterns/UnionFind.java) |
| Expand around center | [src/main/java/com/java/poc/common_patterns/ExpandAroundCenter.java](src/main/java/com/java/poc/common_patterns/ExpandAroundCenter.java) |

## Phase 1: Arrays, Hashing, and String Recall

Objective: restore fast lookup instincts, frequency maps, and array traversal confidence.

Done when: you stop overthinking data structure choice for easy and easy-medium questions.

| Problem | Pattern | Repo File URL | LeetCode URL |
| --- | --- | --- | --- |
| Two Sum | Hash map | [src/main/java/com/java/poc/curatedPracticeList/blind75/TwoSum.java](src/main/java/com/java/poc/curatedPracticeList/blind75/TwoSum.java) | [https://leetcode.com/problems/two-sum/](https://leetcode.com/problems/two-sum/) |
| Contains Duplicate | Set | [src/main/java/com/java/poc/curatedPracticeList/blind75/ContainsDuplicate.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ContainsDuplicate.java) | [https://leetcode.com/problems/contains-duplicate/](https://leetcode.com/problems/contains-duplicate/) |
| Valid Anagram | Frequency counting | [src/main/java/com/java/poc/curatedPracticeList/blind75/ValidAnagram.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ValidAnagram.java) | [https://leetcode.com/problems/valid-anagram/](https://leetcode.com/problems/valid-anagram/) |
| Group Anagrams | Hashing by signature | [src/main/java/com/java/poc/curatedPracticeList/blind75/GroupAnagram.java](src/main/java/com/java/poc/curatedPracticeList/blind75/GroupAnagram.java) | [https://leetcode.com/problems/group-anagrams/](https://leetcode.com/problems/group-anagrams/) |
| Product of Array Except Self | Prefix and suffix | [src/main/java/com/java/poc/curatedPracticeList/blind75/ProductOfArrayExceptSelf.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ProductOfArrayExceptSelf.java) | [https://leetcode.com/problems/product-of-array-except-self/](https://leetcode.com/problems/product-of-array-except-self/) |
| Longest Consecutive Sequence | Hash set | [src/main/java/com/java/poc/curatedPracticeList/blind75/LongestConsecutiveSequence.java](src/main/java/com/java/poc/curatedPracticeList/blind75/LongestConsecutiveSequence.java) | [https://leetcode.com/problems/longest-consecutive-sequence/](https://leetcode.com/problems/longest-consecutive-sequence/) |
| Find the Difference of Two Arrays | Set difference | [src/main/java/com/java/poc/curatedPracticeList/hash_map_set/ArraysDifferenceFinder.java](src/main/java/com/java/poc/curatedPracticeList/hash_map_set/ArraysDifferenceFinder.java) | [https://leetcode.com/problems/find-the-difference-of-two-arrays/](https://leetcode.com/problems/find-the-difference-of-two-arrays/) |
| Determine if Two Strings Are Close | Frequency plus set comparison | [src/main/java/com/java/poc/curatedPracticeList/hash_map_set/StringCloseChecker.java](src/main/java/com/java/poc/curatedPracticeList/hash_map_set/StringCloseChecker.java) | [https://leetcode.com/problems/determine-if-two-strings-are-close/](https://leetcode.com/problems/determine-if-two-strings-are-close/) |
| Unique Number of Occurrences | Map plus set | [src/main/java/com/java/poc/curatedPracticeList/hash_map_set/UniqueOccurrenceChecker.java](src/main/java/com/java/poc/curatedPracticeList/hash_map_set/UniqueOccurrenceChecker.java) | [https://leetcode.com/problems/unique-number-of-occurrences/](https://leetcode.com/problems/unique-number-of-occurrences/) |

## Phase 2: Two Pointers, Sliding Window, and Prefix Sum Speed

Objective: recover the habit of maintaining invariants while scanning once.

Done when: you can identify whether the window is fixed, expanding, shrinking, or at-most-k in under a minute.

| Problem | Pattern | Repo File URL | LeetCode URL |
| --- | --- | --- | --- |
| Valid Palindrome | Two pointers | [src/main/java/com/java/poc/curatedPracticeList/blind75/ValidPalindrome.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ValidPalindrome.java) | [https://leetcode.com/problems/valid-palindrome/](https://leetcode.com/problems/valid-palindrome/) |
| Container With Most Water | Two pointers | [src/main/java/com/java/poc/curatedPracticeList/blind75/ContainerWithMostWater.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ContainerWithMostWater.java) | [https://leetcode.com/problems/container-with-most-water/](https://leetcode.com/problems/container-with-most-water/) |
| 3Sum | Sort plus two pointers | [src/main/java/com/java/poc/curatedPracticeList/blind75/ThreeSum.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ThreeSum.java) | [https://leetcode.com/problems/3sum/](https://leetcode.com/problems/3sum/) |
| Longest Substring Without Repeating Characters | Sliding window | [src/main/java/com/java/poc/curatedPracticeList/blind75/LongestSubstringWithoutRepeatingChar.java](src/main/java/com/java/poc/curatedPracticeList/blind75/LongestSubstringWithoutRepeatingChar.java) | [https://leetcode.com/problems/longest-substring-without-repeating-characters/](https://leetcode.com/problems/longest-substring-without-repeating-characters/) |
| Longest Repeating Character Replacement | Sliding window | [src/main/java/com/java/poc/curatedPracticeList/blind75/LongestRepeatingCharacterReplacement.java](src/main/java/com/java/poc/curatedPracticeList/blind75/LongestRepeatingCharacterReplacement.java) | [https://leetcode.com/problems/longest-repeating-character-replacement/](https://leetcode.com/problems/longest-repeating-character-replacement/) |
| Best Time to Buy and Sell Stock | Running minimum | [src/main/java/com/java/poc/curatedPracticeList/blind75/BestTimeToBuySellStock.java](src/main/java/com/java/poc/curatedPracticeList/blind75/BestTimeToBuySellStock.java) | [https://leetcode.com/problems/best-time-to-buy-and-sell-stock/](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) |
| Max Consecutive Ones III | Shrinking window | [src/main/java/com/java/poc/curatedPracticeList/sliding_window/BinaryArrayMaxOnesCalculator.java](src/main/java/com/java/poc/curatedPracticeList/sliding_window/BinaryArrayMaxOnesCalculator.java) | [https://leetcode.com/problems/max-consecutive-ones-iii/](https://leetcode.com/problems/max-consecutive-ones-iii/) |
| Longest Subarray of 1's After Deleting One Element | Window with one delete | [src/main/java/com/java/poc/curatedPracticeList/sliding_window/BinarySubarrayCalculator.java](src/main/java/com/java/poc/curatedPracticeList/sliding_window/BinarySubarrayCalculator.java) | [https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/](https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/) |
| Maximum Average Subarray I | Fixed window | [src/main/java/com/java/poc/curatedPracticeList/sliding_window/MaxAverageSubarrayCalculator.java](src/main/java/com/java/poc/curatedPracticeList/sliding_window/MaxAverageSubarrayCalculator.java) | [https://leetcode.com/problems/maximum-average-subarray-i/](https://leetcode.com/problems/maximum-average-subarray-i/) |
| Find Pivot Index | Prefix sum | [src/main/java/com/java/poc/curatedPracticeList/prefix_sum/PivotFinder.java](src/main/java/com/java/poc/curatedPracticeList/prefix_sum/PivotFinder.java) | [https://leetcode.com/problems/find-pivot-index/](https://leetcode.com/problems/find-pivot-index/) |

## Phase 3: Stack, Queue, Heap, and Interval Fluency

Objective: get back the instinct for monotonic structures, heap selection, and interval ordering.

Done when: you immediately ask whether order, recency, or min/max retrieval is the real constraint.

| Problem | Pattern | Repo File URL | LeetCode URL |
| --- | --- | --- | --- |
| Decode String | Stack | [src/main/java/com/java/poc/curatedPracticeList/stack/StringDecoder.java](src/main/java/com/java/poc/curatedPracticeList/stack/StringDecoder.java) | [https://leetcode.com/problems/decode-string/](https://leetcode.com/problems/decode-string/) |
| Asteroid Collision | Stack simulation | [src/main/java/com/java/poc/curatedPracticeList/stack/AsteroidCollisionSimulator.java](src/main/java/com/java/poc/curatedPracticeList/stack/AsteroidCollisionSimulator.java) | [https://leetcode.com/problems/asteroid-collision/](https://leetcode.com/problems/asteroid-collision/) |
| Number of Recent Calls | Queue | [src/main/java/com/java/poc/curatedPracticeList/queue/RecentCounter.java](src/main/java/com/java/poc/curatedPracticeList/queue/RecentCounter.java) | [https://leetcode.com/problems/number-of-recent-calls/](https://leetcode.com/problems/number-of-recent-calls/) |
| Dota2 Senate | Queue simulation | [src/main/java/com/java/poc/curatedPracticeList/queue/SenateDecisionSimulator.java](src/main/java/com/java/poc/curatedPracticeList/queue/SenateDecisionSimulator.java) | [https://leetcode.com/problems/dota2-senate/](https://leetcode.com/problems/dota2-senate/) |
| Kth Largest Element in an Array | Heap | [src/main/java/com/java/poc/curatedPracticeList/heap_priority_queue/KthLargestElementFinder.java](src/main/java/com/java/poc/curatedPracticeList/heap_priority_queue/KthLargestElementFinder.java) | [https://leetcode.com/problems/kth-largest-element-in-an-array/](https://leetcode.com/problems/kth-largest-element-in-an-array/) |
| Smallest Number in Infinite Set | Heap plus set | [src/main/java/com/java/poc/curatedPracticeList/heap_priority_queue/SmallestInfiniteSet.java](src/main/java/com/java/poc/curatedPracticeList/heap_priority_queue/SmallestInfiniteSet.java) | [https://leetcode.com/problems/smallest-number-in-infinite-set/](https://leetcode.com/problems/smallest-number-in-infinite-set/) |
| Non-overlapping Intervals | Greedy plus sort | [src/main/java/com/java/poc/curatedPracticeList/intervals/IntervalOverlapRemover.java](src/main/java/com/java/poc/curatedPracticeList/intervals/IntervalOverlapRemover.java) | [https://leetcode.com/problems/non-overlapping-intervals/](https://leetcode.com/problems/non-overlapping-intervals/) |
| Minimum Number of Arrows to Burst Balloons | Interval greedy | [src/main/java/com/java/poc/curatedPracticeList/intervals/ArrowBalloonBurstOptimizer.java](src/main/java/com/java/poc/curatedPracticeList/intervals/ArrowBalloonBurstOptimizer.java) | [https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/](https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/) |
| Daily Temperatures | Monotonic stack | [src/main/java/com/java/poc/curatedPracticeList/monotonic_stack/TemperatureChangeCalculator.java](src/main/java/com/java/poc/curatedPracticeList/monotonic_stack/TemperatureChangeCalculator.java) | [https://leetcode.com/problems/daily-temperatures/](https://leetcode.com/problems/daily-temperatures/) |
| Online Stock Span | Monotonic stack | [src/main/java/com/java/poc/curatedPracticeList/monotonic_stack/StockPriceSpanCalculator.java](src/main/java/com/java/poc/curatedPracticeList/monotonic_stack/StockPriceSpanCalculator.java) | [https://leetcode.com/problems/online-stock-span/](https://leetcode.com/problems/online-stock-span/) |

## Phase 4: Linked List and Binary Search Control

Objective: restore pointer comfort and the habit of proving binary search boundaries carefully.

Done when: you no longer lose time on pointer bookkeeping or off-by-one edge cases.

| Problem | Pattern | Repo File URL | LeetCode URL |
| --- | --- | --- | --- |
| Reverse Linked List | Pointer reversal | [src/main/java/com/java/poc/curatedPracticeList/blind75/ReverseLinkedList.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ReverseLinkedList.java) | [https://leetcode.com/problems/reverse-linked-list/](https://leetcode.com/problems/reverse-linked-list/) |
| Delete the Middle Node of a Linked List | Slow and fast pointers | [src/main/java/com/java/poc/curatedPracticeList/linked_list/LinkedListProcessor.java](src/main/java/com/java/poc/curatedPracticeList/linked_list/LinkedListProcessor.java) | [https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/](https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/) |
| Odd Even Linked List | Pointer rewiring | [src/main/java/com/java/poc/curatedPracticeList/linked_list/OddEvenLinkedListTransformer.java](src/main/java/com/java/poc/curatedPracticeList/linked_list/OddEvenLinkedListTransformer.java) | [https://leetcode.com/problems/odd-even-linked-list/](https://leetcode.com/problems/odd-even-linked-list/) |
| Maximum Twin Sum of a Linked List | Reverse second half | [src/main/java/com/java/poc/curatedPracticeList/linked_list/MaxTwinSumCalculator.java](src/main/java/com/java/poc/curatedPracticeList/linked_list/MaxTwinSumCalculator.java) | [https://leetcode.com/problems/maximum-twin-sum-of-a-linked-list/](https://leetcode.com/problems/maximum-twin-sum-of-a-linked-list/) |
| Koko Eating Bananas | Binary search on answer | [src/main/java/com/java/poc/curatedPracticeList/binary_search/BananaEater.java](src/main/java/com/java/poc/curatedPracticeList/binary_search/BananaEater.java) | [https://leetcode.com/problems/koko-eating-bananas/](https://leetcode.com/problems/koko-eating-bananas/) |
| Find Peak Element | Binary search on structure | [src/main/java/com/java/poc/curatedPracticeList/binary_search/PeakElementFinder.java](src/main/java/com/java/poc/curatedPracticeList/binary_search/PeakElementFinder.java) | [https://leetcode.com/problems/find-peak-element/](https://leetcode.com/problems/find-peak-element/) |
| Search in a Binary Search Tree | Tree-directed search | [src/main/java/com/java/poc/curatedPracticeList/binary_search_trie/BSTSearcher.java](src/main/java/com/java/poc/curatedPracticeList/binary_search_trie/BSTSearcher.java) | [https://leetcode.com/problems/search-in-a-binary-search-tree/](https://leetcode.com/problems/search-in-a-binary-search-tree/) |
| Delete Node in a BST | Recursive structural edit | [src/main/java/com/java/poc/curatedPracticeList/binary_search_trie/BSTNodeDeleter.java](src/main/java/com/java/poc/curatedPracticeList/binary_search_trie/BSTNodeDeleter.java) | [https://leetcode.com/problems/delete-node-in-a-bst/](https://leetcode.com/problems/delete-node-in-a-bst/) |

## Phase 5: Trees, BSTs, and Tries

Objective: recover recursive confidence and level-order traversal speed.

Done when: you can decide quickly between preorder, postorder, inorder, and BFS without trial-and-error.

| Problem | Pattern | Repo File URL | LeetCode URL |
| --- | --- | --- | --- |
| Maximum Depth of Binary Tree | Tree DFS | [src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/BinaryTreeDepthCalculator.java](src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/BinaryTreeDepthCalculator.java) | [https://leetcode.com/problems/maximum-depth-of-binary-tree/](https://leetcode.com/problems/maximum-depth-of-binary-tree/) |
| Count Good Nodes in Binary Tree | DFS with path state | [src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/GoodNodeCounter.java](src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/GoodNodeCounter.java) | [https://leetcode.com/problems/count-good-nodes-in-binary-tree/](https://leetcode.com/problems/count-good-nodes-in-binary-tree/) |
| Lowest Common Ancestor of a Binary Tree | Recursive divide and conquer | [src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/LowestCommonAncestorFinder.java](src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/LowestCommonAncestorFinder.java) | [https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) |
| Path Sum III | DFS with nested traversal | [src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/PathSumCounter.java](src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/PathSumCounter.java) | [https://leetcode.com/problems/path-sum-iii/](https://leetcode.com/problems/path-sum-iii/) |
| Binary Tree Right Side View | Level-order traversal | [src/main/java/com/java/poc/curatedPracticeList/binary_tree_bfs/RightViewTree.java](src/main/java/com/java/poc/curatedPracticeList/binary_tree_bfs/RightViewTree.java) | [https://leetcode.com/problems/binary-tree-right-side-view/](https://leetcode.com/problems/binary-tree-right-side-view/) |
| Maximum Level Sum of a Binary Tree | BFS by level | [src/main/java/com/java/poc/curatedPracticeList/binary_tree_bfs/MaxLevelSumCalculator.java](src/main/java/com/java/poc/curatedPracticeList/binary_tree_bfs/MaxLevelSumCalculator.java) | [https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/](https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/) |
| Implement Trie (Prefix Tree) | Trie basics | [src/main/java/com/java/poc/curatedPracticeList/trie/PrefixTrie.java](src/main/java/com/java/poc/curatedPracticeList/trie/PrefixTrie.java) | [https://leetcode.com/problems/implement-trie-prefix-tree/](https://leetcode.com/problems/implement-trie-prefix-tree/) |
| Search Suggestions System | Trie plus lexicographic suggestions | [src/main/java/com/java/poc/curatedPracticeList/trie/SearchSuggestionsSystem.java](src/main/java/com/java/poc/curatedPracticeList/trie/SearchSuggestionsSystem.java) | [https://leetcode.com/problems/search-suggestions-system/](https://leetcode.com/problems/search-suggestions-system/) |

## Phase 6: Graphs, Backtracking, and DP Recovery

Objective: regain composure on traversal-heavy and state-heavy mediums.

Done when: you can define state, transition, and visited logic before writing code.

| Problem | Pattern | Repo File URL | LeetCode URL |
| --- | --- | --- | --- |
| Keys and Rooms | DFS graph traversal | [src/main/java/com/java/poc/curatedPracticeList/graphs_dfs/RoomAccessChecker.java](src/main/java/com/java/poc/curatedPracticeList/graphs_dfs/RoomAccessChecker.java) | [https://leetcode.com/problems/keys-and-rooms/](https://leetcode.com/problems/keys-and-rooms/) |
| Rotting Oranges | Multi-source BFS | [src/main/java/com/java/poc/curatedPracticeList/graphs_bfs/RottenOrangesCalculator.java](src/main/java/com/java/poc/curatedPracticeList/graphs_bfs/RottenOrangesCalculator.java) | [https://leetcode.com/problems/rotting-oranges/](https://leetcode.com/problems/rotting-oranges/) |
| Number of Islands | Grid DFS | [src/main/java/com/java/poc/dsa/graph/NumberOfIslands.java](src/main/java/com/java/poc/dsa/graph/NumberOfIslands.java) | [https://leetcode.com/problems/number-of-islands/](https://leetcode.com/problems/number-of-islands/) |
| Flood Fill | Graph traversal | [src/main/java/com/java/poc/dsa/graph/FloodFill.java](src/main/java/com/java/poc/dsa/graph/FloodFill.java) | [https://leetcode.com/problems/flood-fill/](https://leetcode.com/problems/flood-fill/) |
| Letter Combinations of a Phone Number | Backtracking | [src/main/java/com/java/poc/curatedPracticeList/backtracking/PhoneNumberCombinations.java](src/main/java/com/java/poc/curatedPracticeList/backtracking/PhoneNumberCombinations.java) | [https://leetcode.com/problems/letter-combinations-of-a-phone-number/](https://leetcode.com/problems/letter-combinations-of-a-phone-number/) |
| Combination Sum | Backtracking | [src/main/java/com/java/poc/curatedPracticeList/backtracking/CombinationSumFinder.java](src/main/java/com/java/poc/curatedPracticeList/backtracking/CombinationSumFinder.java) | [https://leetcode.com/problems/combination-sum/](https://leetcode.com/problems/combination-sum/) |
| N-th Tribonacci Number | 1D DP | [src/main/java/com/java/poc/curatedPracticeList/dp_1d/TribonacciCalculator.java](src/main/java/com/java/poc/curatedPracticeList/dp_1d/TribonacciCalculator.java) | [https://leetcode.com/problems/n-th-tribonacci-number/](https://leetcode.com/problems/n-th-tribonacci-number/) |
| Min Cost Climbing Stairs | 1D DP | [src/main/java/com/java/poc/curatedPracticeList/dp_1d/StairCostCalculator.java](src/main/java/com/java/poc/curatedPracticeList/dp_1d/StairCostCalculator.java) | [https://leetcode.com/problems/min-cost-climbing-stairs/](https://leetcode.com/problems/min-cost-climbing-stairs/) |
| House Robber | Rolling DP | [src/main/java/com/java/poc/curatedPracticeList/dp_1d/HouseRobber.java](src/main/java/com/java/poc/curatedPracticeList/dp_1d/HouseRobber.java) | [https://leetcode.com/problems/house-robber/](https://leetcode.com/problems/house-robber/) |
| Unique Paths | 2D DP | [src/main/java/com/java/poc/curatedPracticeList/dp_multidimensional/GridPathFinder.java](src/main/java/com/java/poc/curatedPracticeList/dp_multidimensional/GridPathFinder.java) | [https://leetcode.com/problems/unique-paths/](https://leetcode.com/problems/unique-paths/) |
| Edit Distance | 2D DP | [src/main/java/com/java/poc/curatedPracticeList/dp_multidimensional/EditDistanceCalculator.java](src/main/java/com/java/poc/curatedPracticeList/dp_multidimensional/EditDistanceCalculator.java) | [https://leetcode.com/problems/edit-distance/](https://leetcode.com/problems/edit-distance/) |
| Best Time to Buy and Sell Stock with Transaction Fee | State machine DP | [src/main/java/com/java/poc/curatedPracticeList/dp_multidimensional/StockTradingProfitCalculator.java](src/main/java/com/java/poc/curatedPracticeList/dp_multidimensional/StockTradingProfitCalculator.java) | [https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/) |

## Phase 7: Blind 75 Consolidation Sprint

Objective: convert pattern recall into interview rhythm.

Done when: you can finish 2 mediums back-to-back without losing structure or missing complexity analysis.

Use this phase as timed rounds. Pick 4 to 6 per sitting.

| Problem | Repo File URL | LeetCode URL |
| --- | --- | --- |
| Climbing Stairs | [src/main/java/com/java/poc/curatedPracticeList/blind75/ClimbingStairs.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ClimbingStairs.java) | [https://leetcode.com/problems/climbing-stairs/](https://leetcode.com/problems/climbing-stairs/) |
| Jump Game | [src/main/java/com/java/poc/curatedPracticeList/blind75/JumpGame.java](src/main/java/com/java/poc/curatedPracticeList/blind75/JumpGame.java) | [https://leetcode.com/problems/jump-game/](https://leetcode.com/problems/jump-game/) |
| Maximum Subarray | [src/main/java/com/java/poc/curatedPracticeList/blind75/MaxSubArraySum.java](src/main/java/com/java/poc/curatedPracticeList/blind75/MaxSubArraySum.java) | [https://leetcode.com/problems/maximum-subarray/](https://leetcode.com/problems/maximum-subarray/) |
| House Robber II | [src/main/java/com/java/poc/curatedPracticeList/blind75/HouseRobber2.java](src/main/java/com/java/poc/curatedPracticeList/blind75/HouseRobber2.java) | [https://leetcode.com/problems/house-robber-ii/](https://leetcode.com/problems/house-robber-ii/) |
| Top K Frequent Elements | [src/main/java/com/java/poc/curatedPracticeList/blind75/TopKFrequentElements.java](src/main/java/com/java/poc/curatedPracticeList/blind75/TopKFrequentElements.java) | [https://leetcode.com/problems/top-k-frequent-elements/](https://leetcode.com/problems/top-k-frequent-elements/) |
| Unique Paths | [src/main/java/com/java/poc/curatedPracticeList/blind75/UniquePaths.java](src/main/java/com/java/poc/curatedPracticeList/blind75/UniquePaths.java) | [https://leetcode.com/problems/unique-paths/](https://leetcode.com/problems/unique-paths/) |
| Longest Palindromic Substring | [src/main/java/com/java/poc/curatedPracticeList/blind75/LongestPalindromicSubstring.java](src/main/java/com/java/poc/curatedPracticeList/blind75/LongestPalindromicSubstring.java) | [https://leetcode.com/problems/longest-palindromic-substring/](https://leetcode.com/problems/longest-palindromic-substring/) |
| Reverse Linked List | [src/main/java/com/java/poc/curatedPracticeList/blind75/ReverseLinkedList.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ReverseLinkedList.java) | [https://leetcode.com/problems/reverse-linked-list/](https://leetcode.com/problems/reverse-linked-list/) |
| Container With Most Water | [src/main/java/com/java/poc/curatedPracticeList/blind75/ContainerWithMostWater.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ContainerWithMostWater.java) | [https://leetcode.com/problems/container-with-most-water/](https://leetcode.com/problems/container-with-most-water/) |
| Best Time to Buy and Sell Stock | [src/main/java/com/java/poc/curatedPracticeList/blind75/BestTimeToBuySellStock.java](src/main/java/com/java/poc/curatedPracticeList/blind75/BestTimeToBuySellStock.java) | [https://leetcode.com/problems/best-time-to-buy-and-sell-stock/](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) |
| Longest Substring Without Repeating Characters | [src/main/java/com/java/poc/curatedPracticeList/blind75/LongestSubstringWithoutRepeatingChar.java](src/main/java/com/java/poc/curatedPracticeList/blind75/LongestSubstringWithoutRepeatingChar.java) | [https://leetcode.com/problems/longest-substring-without-repeating-characters/](https://leetcode.com/problems/longest-substring-without-repeating-characters/) |
| Group Anagrams | [src/main/java/com/java/poc/curatedPracticeList/blind75/GroupAnagram.java](src/main/java/com/java/poc/curatedPracticeList/blind75/GroupAnagram.java) | [https://leetcode.com/problems/group-anagrams/](https://leetcode.com/problems/group-anagrams/) |

## Fast Restart Rules

- Do not start with hard problems. Your bottleneck is speed and crispness, not depth.
- Prefer solving from a blank file over rereading old answers.
- When you miss a problem, record the missed invariant, not just the final trick.
- Re-solve the same medium 48 hours later. Fast recall matters more than first-pass novelty.
- In interview prep mode, depth-first repetition beats breadth-first exploration.

## Weekend Shortlist

If you only have one weekend, do these 12 in order.

| Order | Problem | Repo File URL | LeetCode URL |
| --- | --- | --- | --- |
| 1 | Two Sum | [src/main/java/com/java/poc/curatedPracticeList/blind75/TwoSum.java](src/main/java/com/java/poc/curatedPracticeList/blind75/TwoSum.java) | [https://leetcode.com/problems/two-sum/](https://leetcode.com/problems/two-sum/) |
| 2 | Product of Array Except Self | [src/main/java/com/java/poc/curatedPracticeList/blind75/ProductOfArrayExceptSelf.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ProductOfArrayExceptSelf.java) | [https://leetcode.com/problems/product-of-array-except-self/](https://leetcode.com/problems/product-of-array-except-self/) |
| 3 | Valid Palindrome | [src/main/java/com/java/poc/curatedPracticeList/blind75/ValidPalindrome.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ValidPalindrome.java) | [https://leetcode.com/problems/valid-palindrome/](https://leetcode.com/problems/valid-palindrome/) |
| 4 | Longest Substring Without Repeating Characters | [src/main/java/com/java/poc/curatedPracticeList/blind75/LongestSubstringWithoutRepeatingChar.java](src/main/java/com/java/poc/curatedPracticeList/blind75/LongestSubstringWithoutRepeatingChar.java) | [https://leetcode.com/problems/longest-substring-without-repeating-characters/](https://leetcode.com/problems/longest-substring-without-repeating-characters/) |
| 5 | Decode String | [src/main/java/com/java/poc/curatedPracticeList/stack/StringDecoder.java](src/main/java/com/java/poc/curatedPracticeList/stack/StringDecoder.java) | [https://leetcode.com/problems/decode-string/](https://leetcode.com/problems/decode-string/) |
| 6 | Reverse Linked List | [src/main/java/com/java/poc/curatedPracticeList/blind75/ReverseLinkedList.java](src/main/java/com/java/poc/curatedPracticeList/blind75/ReverseLinkedList.java) | [https://leetcode.com/problems/reverse-linked-list/](https://leetcode.com/problems/reverse-linked-list/) |
| 7 | Koko Eating Bananas | [src/main/java/com/java/poc/curatedPracticeList/binary_search/BananaEater.java](src/main/java/com/java/poc/curatedPracticeList/binary_search/BananaEater.java) | [https://leetcode.com/problems/koko-eating-bananas/](https://leetcode.com/problems/koko-eating-bananas/) |
| 8 | Maximum Depth of Binary Tree | [src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/BinaryTreeDepthCalculator.java](src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/BinaryTreeDepthCalculator.java) | [https://leetcode.com/problems/maximum-depth-of-binary-tree/](https://leetcode.com/problems/maximum-depth-of-binary-tree/) |
| 9 | Lowest Common Ancestor of a Binary Tree | [src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/LowestCommonAncestorFinder.java](src/main/java/com/java/poc/curatedPracticeList/binary_tree_dfs/LowestCommonAncestorFinder.java) | [https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) |
| 10 | Keys and Rooms | [src/main/java/com/java/poc/curatedPracticeList/graphs_dfs/RoomAccessChecker.java](src/main/java/com/java/poc/curatedPracticeList/graphs_dfs/RoomAccessChecker.java) | [https://leetcode.com/problems/keys-and-rooms/](https://leetcode.com/problems/keys-and-rooms/) |
| 11 | Combination Sum | [src/main/java/com/java/poc/curatedPracticeList/backtracking/CombinationSumFinder.java](src/main/java/com/java/poc/curatedPracticeList/backtracking/CombinationSumFinder.java) | [https://leetcode.com/problems/combination-sum/](https://leetcode.com/problems/combination-sum/) |
| 12 | House Robber | [src/main/java/com/java/poc/curatedPracticeList/dp_1d/HouseRobber.java](src/main/java/com/java/poc/curatedPracticeList/dp_1d/HouseRobber.java) | [https://leetcode.com/problems/house-robber/](https://leetcode.com/problems/house-robber/) |

That set is enough to restore most interview muscle memory.