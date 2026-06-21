export default function (plop) {
  // Generator tạo Next.js Service (BFF caller)
  plop.setGenerator('next-service', {
    description: 'Tạo một file service giao tiếp API cho Next.js',
    prompts: [
      {
        type: 'input',
        name: 'name',
        message: 'Tên service (ví dụ: User, Product):'
      }
    ],
    actions: [
      {
        type: 'add',
        path: 'nextjs-base/services/{{camelCase name}}.service.ts',
        templateFile: '.plop/templates/next-service.hbs'
      }
    ]
  });
}
