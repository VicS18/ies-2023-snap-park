# Dockerfile for the Python container
FROM python:3.9

WORKDIR /app

COPY requirements.txt /app/

RUN pip install --upgrade pip && \
    pip install -r requirements.txt

COPY . /app/

EXPOSE 7070

CMD ["python", "generator.py"]
