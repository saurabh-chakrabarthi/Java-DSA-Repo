from fpdf import FPDF

# Create a PDF class
class PDF(FPDF):
    def header(self):
        self.set_font("Arial", "B", 14)
        self.cell(0, 10, "Java LeetCode Coding Cheatsheet", ln=True, align="C")
        self.ln(5)

    def chapter_title(self, title):
        self.set_font("Arial", "B", 12)
        self.set_text_color(0, 0, 128)
        self.cell(0, 10, title, ln=True)
        self.set_text_color(0, 0, 0)

    def code_block(self, code):
        self.set_font("Courier", "", 10)
        self.set_fill_color(240, 240, 240)
        for line in code.split('\n'):
            self.multi_cell(0, 5, line, 0, 1, fill=True)
        self.ln(1)

    def add_tip(self, title, code):
        self.chapter_title(title)
        self.code_block(code)


# Initialize PDF
pdf = PDF()
pdf.set_auto_page_break(auto=True, margin=15)
pdf.add_page()

# Data for the cheatsheet
tips = [
    ("Find Duplicates → Use HashSet",
    '''Set<Integer> seen = new HashSet<>();
for (int num : nums) {
    if (!seen.add(num)) return true;
}
return false;'''),

    ("Two Sum → Use HashMap",
    '''Map<Integer, Integer> map = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int complement = target - nums[i];
    if (map.containsKey(complement)) return new int[]{map.get(complement), i};
    map.put(nums[i], i);
}'''),

    ("Max Sum Subarray of Size K",
    '''int maxSum = 0, windowSum = 0;
for (int i = 0; i < k; i++) windowSum += nums[i];
maxSum = windowSum;

for (int i = k; i < nums.length; i++) {
    windowSum += nums[i] - nums[i - k];
    maxSum = Math.max(maxSum, windowSum);
}'''),

    ("Reverse Array In-Place",
    '''int left = 0, right = nums.length - 1;
while (left < right) {
    int temp = nums[left];
    nums[left++] = nums[right];
    nums[right--] = temp;
}'''),

    ("Is Palindrome (String)",
    '''int l = 0, r = s.length() - 1;
while (l < r) {
    if (s.charAt(l) != s.charAt(r)) return false;
    l++;
    r--;
}
return true;''')
]

# Add tips to PDF
for title, code in tips:
    pdf.add_tip(title, code)

# Save PDF
pdf_path = "/Java_LeetCode_Cheatsheet.pdf"
pdf.output(pdf_path)
