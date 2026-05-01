package com.java.poc;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

public class Blind75CheatSheet {

    public static void main(String[] args) {
        String pdfPath = "Blind75_CheatSheet_Java.pdf";

        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("BLIND 75 CHEAT SHEET - JAVA PATTERNS & EXAMPLES")
                    .setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("====================================================\n\n"));

            writeSection(document, "Arrays & Hashing",
                    "Use HashMap for frequency count or checking complements.",
                    "Map<Integer, Integer> map = new HashMap<>();\nfor (int num : nums) {\n    map.put(num, map.getOrDefault(num, 0) + 1);\n}");

            writeSection(document, "Two Pointers",
                    "Common in sorted arrays or linked lists for pair problems.",
                    "int left = 0, right = nums.length - 1;\nwhile (left < right) {\n    int sum = nums[left] + nums[right];\n    if (sum == target) return true;\n    else if (sum < target) left++;\n    else right--;\n}");

            writeSection(document, "Sliding Window",
                    "Used to track a window of elements in a linear array.",
                    "int max = 0, sum = 0, k = 3;\nfor (int i = 0; i < nums.length; i++) {\n    sum += nums[i];\n    if (i >= k) sum -= nums[i - k];\n    max = Math.max(max, sum);\n}");

            writeSection(document, "Stack",
                    "Useful for Next Greater Element, Valid Parentheses, etc.",
                    "Stack<Character> stack = new Stack<>();\nfor (char c : s.toCharArray()) {\n    if (c == '(') stack.push(c);\n    else if (stack.isEmpty() || stack.pop() != '(') return false;\n}\nreturn stack.isEmpty();");

            writeSection(document, "Binary Search",
                    "Always on sorted arrays or to minimize/maximize answers.",
                    "int l = 0, r = nums.length - 1;\nwhile (l <= r) {\n    int mid = (l + r) / 2;\n    if (nums[mid] == target) return mid;\n    else if (nums[mid] < target) l = mid + 1;\n    else r = mid - 1;\n}");

            writeSection(document, "Linked List",
                    "Use fast and slow pointers to detect cycles.",
                    "ListNode slow = head, fast = head;\nwhile (fast != null && fast.next != null) {\n    slow = slow.next;\n    fast = fast.next.next;\n    if (slow == fast) return true;\n}");

            writeSection(document, "Trees",
                    "Use recursion for DFS on binary trees.",
                    "void inorder(TreeNode root) {\n    if (root == null) return;\n    inorder(root.left);\n    System.out.print(root.val);\n    inorder(root.right);\n}");

            writeSection(document, "Tries",
                    "Used for word prefix matching.",
                    "class TrieNode {\n    TrieNode[] children = new TrieNode[26];\n    boolean isWord = false;\n}");

            writeSection(document, "Heap / Priority Queue",
                    "Used for Kth largest/smallest, sorting by priority.",
                    "PriorityQueue<Integer> minHeap = new PriorityQueue<>();\nfor (int num : nums) {\n    minHeap.offer(num);\n    if (minHeap.size() > k) minHeap.poll();\n}");

            writeSection(document, "Backtracking",
                    "Used in permutations, subsets, N-Queens, Sudoku.",
                    "void backtrack(List<List<Integer>> res, List<Integer> temp, int[] nums, boolean[] used) {\n    if (temp.size() == nums.length) res.add(new ArrayList<>(temp));\n    for (int i = 0; i < nums.length; i++) {\n        if (used[i]) continue;\n        used[i] = true;\n        temp.add(nums[i]);\n        backtrack(res, temp, nums, used);\n        used[i] = false;\n        temp.remove(temp.size() - 1);\n    }\n}");

            writeSection(document, "Graphs (BFS/DFS)",
                    "Use BFS for shortest path, DFS for full traversal.",
                    "Queue<Integer> q = new LinkedList<>();\nSet<Integer> visited = new HashSet<>();\nq.offer(start);\nwhile (!q.isEmpty()) {\n    int node = q.poll();\n    if (!visited.contains(node)) {\n        visited.add(node);\n        for (int neighbor : graph.get(node)) {\n            q.offer(neighbor);\n        }\n    }\n}");

            writeSection(document, "Dynamic Programming",
                    "Used when subproblems repeat, use memo or bottom-up table.",
                    "int[] dp = new int[n+1];\ndp[0] = 1;\nfor (int i = 1; i <= n; i++) {\n    dp[i] = dp[i-1] + dp[i-2];\n}");

            writeSection(document, "Greedy",
                    "Make locally optimal choices (Interval Scheduling, Jump Game).",
                    "int end = 0, farthest = 0, jumps = 0;\nfor (int i = 0; i < nums.length - 1; i++) {\n    farthest = Math.max(farthest, i + nums[i]);\n    if (i == end) {\n        jumps++;\n        end = farthest;\n    }\n}");

            writeSection(document, "Intervals",
                    "Sort by start or end time and merge or count overlaps.",
                    "Arrays.sort(intervals, (a, b) -> a[0] - b[0]);\nList<int[]> merged = new ArrayList<>();\nfor (int[] interval : intervals) {\n    if (merged.isEmpty() || merged.get(merged.size()-1)[1] < interval[0]) {\n        merged.add(interval);\n    } else {\n        merged.get(merged.size()-1)[1] = Math.max(merged.get(merged.size()-1)[1], interval[1]);\n    }\n}");

            writeSection(document, "Bit Manipulation",
                    "Use XOR to cancel out duplicate numbers.",
                    "int res = 0;\nfor (int num : nums) res ^= num;\nreturn res;");

            writeSection(document, "Prefix Sum",
                    "Build a running total array to quickly find ranges.",
                    "int[] prefix = new int[nums.length+1];\nfor (int i = 0; i < nums.length; i++) {\n    prefix[i+1] = prefix[i] + nums[i];\n}\nint sumRange = prefix[j+1] - prefix[i];");

            writeSection(document, "Union Find (Disjoint Set)",
                    "Use for connected components or cycle detection.",
                    "int[] parent = new int[n];\nfor (int i = 0; i < n; i++) parent[i] = i;\n\nint find(int x) {\n    if (parent[x] != x) parent[x] = find(parent[x]);\n    return parent[x];\n}\n\nvoid union(int x, int y) {\n    parent[find(x)] = find(y);\n}");

            document.add(new Paragraph("\n✅ End of Cheat Sheet. Happy Coding!\n"));

            document.close();
            System.out.println("Cheat sheet PDF generated: " + pdfPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeSection(Document document, String title, String desc, String code) {
        document.add(new Paragraph("🟢 " + title).setBold().setFontSize(13));
        document.add(new Paragraph(desc).setItalic());
        document.add(new Paragraph(code).setFontSize(10));
        document.add(new Paragraph("\n"));
    }
}
